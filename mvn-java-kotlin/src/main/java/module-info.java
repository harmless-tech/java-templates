// TODO: open?
@org.jspecify.annotations.NullMarked
module tech.harmless.TODO {
    requires org.jspecify;
    requires org.tinylog.api;
    requires org.tinylog.impl;

    // Java Stdlib
    requires java.base;
    requires java.compiler;

    // Kotlin Stdlib
    requires kotlin.stdlib;

    exports tech.harmless.TODO;
}
