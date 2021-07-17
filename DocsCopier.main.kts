#!/usr/bin/env kotlin

/*
 * Copyright 2021 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

@file:Repository("https://repo.maven.apache.org/maven2/")
//@file:Repository("https://oss.sonatype.org/content/repositories/snapshots")
//@file:Repository("file:///Users/louiscad/.m2/repository")
@file:DependsOn("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.0")

@file:CompilerOptions("-Xopt-in=kotlin.RequiresOptIn")
@file:Suppress("experimental_is_not_enabled")

import kotlinx.coroutines.*
import java.io.File
import java.nio.file.Paths
import kotlin.time.ExperimentalTime
import kotlin.time.measureTime

val projectDir: File = Paths.get("").toFile().let { currentDir: File ->
    when (val dirName = currentDir.absoluteFile.name) {
        "docs" -> currentDir.absoluteFile.parentFile
        "Splitties" -> currentDir
        else -> currentDir.also {
            System.err.println("Warning: unexpected name for the current dir:")
            System.err.println("dir name: $dirName")
            System.err.println("full path: $currentDir")
        }
    }
}

val docsDir = projectDir.resolve("docs")

// Regex inspired by http://blog.michaelperrin.fr/2019/02/04/advanced-regular-expressions/
// If needed, look at the article again to support nested brackets (not needed so far).
//language=RegExp
val linksRegex = """\[(?<text>.+)\]\((?<url>[^) ]+)(?: \"(?<title>.+)\")?\)""".toRegex()
val gitMainBranchUrl = "https://github.com/LouisCAD/Splitties/tree/main"

suspend fun readTextWithAdaptationForMkDocs(sourceFile: File): String = Dispatchers.Default {
    Dispatchers.IO {
        sourceFile.readText()
    }.replace(linksRegex) withRelativeLinksTranslated@{ matchResult ->
        val groups = matchResult.groups
        val urlGroup = groups[2]!!
        val url: String = urlGroup.value.trim()
        if ("://" in url || url.startsWith('#')) { // Leave external and same-page links.
            return@withRelativeLinksTranslated matchResult.value
        }
        if (url.substringBeforeLast('#').endsWith(".md")) { // Leave relative links to markdown files.
            return@withRelativeLinksTranslated matchResult.value
        }
        if ('.' !in url.substringAfterLast('/', missingDelimiterValue = ".")) { // Leave links to paths.
            return@withRelativeLinksTranslated matchResult.value
        }
        // We want to rewrite relative links to files that are not part of the documentation (e.g. Kotlin files)
        // to links pointing to the link in the GitHub repository.
        val sourceDir = sourceFile.parentFile ?: projectDir
        val newPath = sourceDir.relativeTo(projectDir).resolve(url).normalize()
        val replacement = "$gitMainBranchUrl/$newPath"
        val offset = -matchResult.range.first
        val rangeToReplaceInMatch = (urlGroup.range.first + offset)..(urlGroup.range.last + offset)
        matchResult.value.replaceRange(rangeToReplaceInMatch, replacement)
    }
}


suspend fun copyAdaptedTopLevelMarkdownFiles(): Unit = coroutineScope {
    val namesOfTopLevelMarkdownFiles = listOf(
        "README.md", "CHANGELOG.md", "Comparison_with_anko.md"
    )
    namesOfTopLevelMarkdownFiles.forEach { fileName ->
        launch {
            val newContent = readTextWithAdaptationForMkDocs(projectDir.resolve(fileName))
            val targetFile = docsDir.resolve(fileName)
            Dispatchers.IO {
                targetFile.writeText(newContent)
            }
        }
    }
}

suspend fun copyAdaptedMarkdownFilesFromModules(): Unit = coroutineScope {
    val modulesDir = projectDir.resolve("modules")
    val dirsWithReadme: List<File> = Dispatchers.IO {
        modulesDir.listFiles { file ->
            file.isDirectory && file.resolve("README.md").exists()
        }!!.asList()
    }
    val destinationDir = docsDir.resolve("modules")
    dirsWithReadme.forEach { dir ->
        Dispatchers.IO {
            dir.list()
        }?.forEach { fileName ->
            if (fileName.endsWith(".md")) launch {
                val targetDir = destinationDir.resolve(dir.name)
                val targetFile = targetDir.resolve(fileName)
                val newContent = readTextWithAdaptationForMkDocs(sourceFile = dir.resolve(fileName))
                Dispatchers.IO {
                    targetDir.mkdirs()
                    targetFile.writeText(newContent)
                }
            }
        }
    }
}

println("Copying the docs…")

@OptIn(ExperimentalTime::class)
val copyDuration = measureTime {
    runBlocking(Dispatchers.Default) {
        launch { copyAdaptedTopLevelMarkdownFiles() }
        copyAdaptedMarkdownFilesFromModules()
    }
}

println("Done! Took $copyDuration")
