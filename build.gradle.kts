// Top-level build file where you can add configuration options common to all sub-projects/modules.

//for all project. there can be more than one applications in one project
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
}