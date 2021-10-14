import 'package:flutter/services.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:flutter_simpl_fingerprint/flutter_simpl_fingerprint.dart';

void main() {
  const MethodChannel channel = MethodChannel('flutter_simpl_fingerprint');

  TestWidgetsFlutterBinding.ensureInitialized();

  setUp(() {
    channel.setMockMethodCallHandler((MethodCall methodCall) async {
      return '42';
    });
  });

  tearDown(() {
    channel.setMockMethodCallHandler(null);
  });

  test('getPlatformVersion', () async {
    expect(await FlutterSimplFingerprint.platformVersion, '42');
  });
}
