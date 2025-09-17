/*
 * Copyright (c) 2025 harmless-tech
 * SPDX-License-Identifier: TODO
 *
 * TODO
 */
package tech.harmless.TODO

import org.tinylog.Logger

/** This is a class! */
class KApp() {}

fun main() {
    System.setProperty("logger.dir", "./tmp/TODO/logs")
    Logger.info { "App started!" }
}
