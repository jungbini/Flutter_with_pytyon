package com.example.myapp

import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel
import com.chaquo.python.Python
import com.chaquo.python.android.AndroidPlatform

class MainActivity: FlutterActivity() {
    private val CHANNEL = "com.example.myapp/python" // Flutter와 동일한 채널명 사용

    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)

        MethodChannel(flutterEngine.dartExecutor.binaryMessenger, CHANNEL).setMethodCallHandler {
            call, result ->
            if (!Python.isStarted()) {
                Python.start(AndroidPlatform(this))
            }
            val py = Python.getInstance()

            when (call.method) {
                "runPythonGreet" -> {
                    try {
                        val savePath = filesDir.absolutePath
                        val pythonModule = py.getModule("my_script") // Python 파일명 (확장자 제외)
                        val greeting = pythonModule.callAttr("greet", savePath).toString()
                        result.success(greeting)
                    } catch (e: Exception) {
                        result.error("PYTHON_ERROR", e.toString(), e.toString())
                    }

                }
                "runPythonAddArrays" -> {
                    val list1 = call.argument<List<Int>>("list1")
                    val list2 = call.argument<List<Int>>("list2")
                    if (list1 != null && list2 != null) {
                        try {
                            val pythonModule = py.getModule("my_script")
                            // Python 함수는 Java/Kotlin List를 Python List로 자동 변환 가능
                            val sumList = pythonModule.callAttr("add_arrays", list1, list2).asList()
                            result.success(sumList)
                        } catch (e: Exception) {
                            result.error("PYTHON_ERROR", "Error running Python add_arrays function", e.toString())
                        }
                    } else {
                        result.error("ARGUMENT_ERROR", "List arguments are missing", null)
                    }
                }
                else -> {
                    result.notImplemented()
                }
            }
        }
    }
}
