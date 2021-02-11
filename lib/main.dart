import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

void main() => runApp(MyApp());

class MyApp extends StatelessWidget {

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Floating Button',
      debugShowCheckedModeBanner: false,
      home: Home(),
    );
  }
}

class Home extends StatefulWidget {

  @override
  _HomeState createState() => _HomeState();
}

class _HomeState extends State<Home> {
  static const platform = const MethodChannel('floating_button');
  int count = 0;

  @override
  void initState() {
    super.initState();

    platform.setMethodCallHandler((methodCall) async {
      if(methodCall.method == "touch"){
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
          children: <Widget>[
            Container(
              padding: const EdgeInsets.only(bottom: 24),
              child: Text(
                "$count",
                textAlign: TextAlign.center,
                style: TextStyle(
                  fontSize: 72,
                  fontWeight: FontWeight.w800,
                ),
              ),
            ),
            RaisedButton(
              child: Text("Create"),
              onPressed: (){
                platform.invokeMethod("create");
              }
            ),
            RaisedButton(
              child: Text("Show"),
              onPressed: () async {
                platform.invokeMethod("show");
              }
            ),
            RaisedButton(
              child: Text("Hide"),
              onPressed: () async {
                platform.invokeMethod("hide");
              }
            ),
          ],
        ),
      ),
    );
  }
}
