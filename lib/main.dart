// lib/main.dart 또는 다른 Dart 파일
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(home: MyHomePage());
  }
}

class MyHomePage extends StatefulWidget {
  const MyHomePage({super.key});

  @override
  _MyHomePageState createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  // 안드로이드 네이티브 코드에서 정의한 채널명과 동일해야 함
  static const platform = MethodChannel('com.example.myapp/python');

  String _pythonGreeting = 'Waiting for Python greeting...';
  List<dynamic>?
  _pythonSumResult; // Python에서 List<int>를 반환해도 dynamic으로 받는 것이 안전

  Future<void> _getPythonGreeting() async {
    String greeting;
    try {
      final String result = await platform.invokeMethod('runPythonGreet');
      greeting = result;
    } on PlatformException catch (e) {
      greeting = "Failed to get greeting: '${e.message}'.";
    }

    setState(() {
      _pythonGreeting = greeting;
    });
  }

  Future<void> _getPythonSum() async {
    List<dynamic>? sumResult;
    try {
      final List<dynamic> result = await platform.invokeMethod(
        'runPythonAddArrays',
        {
          'list1': [1, 2, 3],
          'list2': [4, 5, 6],
        },
      );
      sumResult = result;
    } on PlatformException catch (e) {
      print("Failed to get sum: '${e.message}'.");
      sumResult = null;
    }

    setState(() {
      _pythonSumResult = sumResult;
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: Text('Flutter with Python (Chaquopy)')),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            Text(_pythonGreeting),
            SizedBox(height: 20),
            ElevatedButton(
              onPressed: _getPythonGreeting,
              child: Text('Get Greeting from Python'),
            ),
            SizedBox(height: 20),
            if (_pythonSumResult != null)
              Text('Python Sum Result: $_pythonSumResult'),
            SizedBox(height: 20),
            ElevatedButton(
              onPressed: _getPythonSum,
              child: Text('Add Arrays in Python'),
            ),
          ],
        ),
      ),
    );
  }
}
