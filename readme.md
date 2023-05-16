CounterTextView
===============

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT) [![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-CounterTextView-brightgreen.svg?style=flat)](https://android-arsenal.com/details/1/6445) [![](https://jitpack.io/v/ursimon/CounterTextView.svg)](https://jitpack.io/#ursimon/CounterTextView)

As a part of development of [Minitris](https://play.google.com/store/apps/details?id=com.ucisoftware.minitris) 
there was a need for a score display that would indicate to the user that the score 
is increasing and would [provide a visual feedback](https://www.youtube.com/watch?v=OKFIJ9Bxirg).

Introducing CounterTextView
---------------------------

It is a standard `TextView` subclass driven by `RxJava` `Observable.interval` towards defined numerical target,
which you can specify either by standard `setTarget(long target)` or you can tie it to your Rx stream
via public  `Action1<Long> targetAction()` (RxJava1) or `Consumer<Long> targetConsumer()` (RxJava2)
respectively.

Samples
-------

You can see usage examples in `sample-rxjava1` and `sample-rxjava2` modules.

![CounterTextView](https://raw.githubusercontent.com/ursimon/CounterTextView/master/countertextview.gif)

Usage
-----

Add [Jetpack.io](https://jitpack.io/#ursimon/CounterTextView/) repository to your project level `build.gradle`:

```
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

and add one of these dependencies:

RxJava1 based:

```
dependencies {
        implementation 'com.github.ursimon.CounterTextView:library-rxjava1:1.1.1'
        implementation 'com.github.ursimon.CounterTextView:library-common:1.1.1'
}
```

RxJava2 based:

```
dependencies {
        implementation 'com.github.ursimon.CounterTextView:library-rxjava2:1.1.1'
        implementation 'com.github.ursimon.CounterTextView:library-common:1.1.1'
}
```

Then you can declare it in your XML layout similar to standard `TextView`:

```
    <cz.ursiny.countertextview.library.CounterTextView
        android:id="@+id/counter"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        app:numberFormat="true"
        app:speed="100"
        />
```

It can take up two optional xml parameters:

param        | value      | description
------------ | ---------- | -----------
numberFormat | true/false | whether you want to be number formatted by default `NumberFormat.getInstance().format` 
speed        | int        | speed of Observable.interval ticking to animate towards target, default **25** milliseconds

#### From code

You can use base method:

`setTarget(long target)` and that will trigger animation towards it or will retarget currently running one

or you can tie it to your rx stream directly using `Action1<Long>`, `Consumer<Long>` 
depending on RxJava1/RxJava2 implementation you choose to use
