package com.simpl.flutter_simpl_fingerprint;

import android.content.Context;
import androidx.annotation.NonNull;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;
import com.simpl.android.fingerprint.SimplFingerprint;
import com.simpl.android.fingerprint.SimplFingerprintListener;

import java.util.Map;

/** FlutterSimplFingerprintPlugin */
public class FlutterSimplFingerprintPlugin implements FlutterPlugin, MethodCallHandler {
  private MethodChannel channel;

  private Context context;

  @Override
  public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
    channel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), "flutter_simpl_fingerprint");
    channel.setMethodCallHandler(this);
    context = flutterPluginBinding.applicationContext;
  }

  @Override
  public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
    if (call.method.equals("generateFingerprint")) {
      Map<String, String> args = (Map<String, String>)call.arguments;
      String fp = generateFingerprint(args.get("phoneNumber"), args.get("emailId"));
      result.success(fp);
    } else {
      result.notImplemented();
    }
  }

  @Override
  public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
    channel.setMethodCallHandler(null);
  }

  public String generateFingerprint(final String phoneNumber, final String emailId) {
    SimplFingerprint.init(context, phoneNumber, emailId);
    final String[] fingerprint = new String[1];
    SimplFingerprint.getInstance().generateFingerprint(new SimplFingerprintListener() {
      @Override
      public void fingerprintData(String fp) {
        fingerprint[0] = fp;
      }
    });
    return fingerprint[0];
  }
}
