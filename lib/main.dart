import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

void main() => runApp(const MyApp());

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return const MaterialApp(
      title: 'Floating Button',
      debugShowCheckedModeBanner: false,
      home: Home(),
    );
  }
}

class Home extends StatefulWidget {
  const Home({super.key});

  @override
  State<StatefulWidget> createState() => _HomeState();
}

class _HomeState extends State<Home> {
  static const platform = const MethodChannel('floating_button');
  int count = 0;

  @override
  void initState() {
    super.initState();

    platform.setMethodCallHandler((methodCall) async {
      if (methodCall.method == "touch") {
        setState(() {
          count += 1;
        });
      }
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text("Floating Button"),
        centerTitle: true,
      ),
      body: Container(
        color: Colors.blue[50],
        padding: const EdgeInsets.symmetric(horizontal: 32),
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          crossAxisAlignment: CrossAxisAlignment.stretch,
          children: [
            Container(
              padding: const EdgeInsets.only(bottom: 24),
              child: Text(
                "$count",
                textAlign: TextAlign.center,
                style: const TextStyle(
                  fontSize: 72,
                  fontWeight: FontWeight.w800,
                ),
              ),
            ),
            TextButton(
              child: Text("Create"),
              onPressed: () {
                platform.invokeMethod("create");
              },
            ),
            TextButton(
              child: Text("Show"),
              onPressed: () {
                platform.invokeMethod("show");
              },
            ),
            TextButton(
              child: Text("Hide"),
              onPressed: () {
                platform.invokeMethod("hide");
              },
            ),
          ],
        ),
      ),
    );
  }
}
