package com.youdanhui.flutter_tts;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.view.KeyEvent;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;

import com.realsil.sdk.bbpro.BeeProManager;
import com.realsil.sdk.bbpro.BumblebeeCallback;
import com.realsil.sdk.bbpro.BumblebeeService;
import com.realsil.sdk.bbpro.BumblebeeTool;
import com.realsil.sdk.bbpro.RtkBbpro;
import com.realsil.sdk.bbpro.core.protocol.CommandContract;
import com.realsil.sdk.bbpro.tts.TtsManager;
import com.realsil.sdk.core.Constants;
import com.realsil.sdk.core.bluetooth.scanner.SpecScanRecord;
import com.realsil.sdk.core.logger.WriteLog;
import com.realsil.sdk.core.logger.ZLogger;
import com.realsil.sdk.support.base.BaseActivity;
import com.realsil.sdk.support.utilities.PluginsManager;
import com.realsil.sdk.tts.baidu.BaiduTtsEngine;

import java.util.ArrayList;
import java.util.Iterator;

import butterknife.ButterKnife;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;

/** FlutterTtsPlugin */
public class FlutterTtsPlugin implements MethodCallHandler {
  private final Registrar registrar;

  private final BeeProManager mBeeProManager;

  /** Plugin registration. */
  public static void registerWith(Registrar registrar) {
    System.out.println("===============================================11111111111");

    final MethodChannel channel = new MethodChannel(registrar.messenger(), "flutter_tts");
    channel.setMethodCallHandler(new FlutterTtsPlugin(registrar));


    WriteLog.install(registrar.context(), "Tts", 2);

    BumblebeeTool.transmitClass(registrar.activity().getClass());
//        BeeProManager.SERVER_ROLE_ENABLED = true;

    BeeProManager.SERVICE_MODE = BumblebeeService.MODE_AUTO_CONNECT_WHEN_START;
    BeeProManager.CLIENT_AUTO_CONNECT_SPP_ENABLED = true;
    BeeProManager.MODULE_SUPPORTED = BeeProManager.MODULE_TTS;

    RtkBbpro.initialize(registrar.context());
    int pid = android.os.Process.myPid();
    System.out.println("===============================================11111111111");
//    String processAppName = getProcessName(this, pid);
////        ZLogger.v("process: " + processAppName);
//    // 如果app启用了远程的service，此application:onCreate会被调用2次
//    // 默认的app会在以包名为默认的process name下运行，如果查到的process name不是app的process name就立即返回
//    if (processAppName != null && processAppName.equalsIgnoreCase(getPackageName())) {
//      PluginsManager.configBugly(this, BuildConfig.FLAVOR, "2bc4b5d81f");
//
//      BaiduTtsEngine.configTts("11723798", "W4wd4wU05ebBRPgAeZl9M1ey", "73wHt8Mo7m6pL9XAXoOof5oHR4OSZYfq");
//      TtsManager.getInstance(this).setTtsEngine(new BaiduTtsEngine(this));
//
//      //get the BumblebeeTool proxy , get mBumblebeeTool through callback
//      BeeProManager.getInstance(this).addManagerCallback(mBumblebeeCallback);
//    }

    ButterKnife.bind(registrar.activity());

    WriteLog.getInstance().startLog();


    BeeProManager mBeeProManager;
    mBeeProManager = BeeProManager.getInstance(registrar.activeContext());
    mBeeProManager.addManagerCallback(new BumblebeeCallback() {
      @Override
      public void onServiceConnectionStateChanged(boolean status, BumblebeeTool bbpro) {
        super.onServiceConnectionStateChanged(status, bbpro);
        ZLogger.d("status=" + status);
//      runOnUiThread(() -> checkState());
      }

      @Override
      public void onConnectionStateChanged(BluetoothDevice device, int connectType, int state) {
        super.onConnectionStateChanged(device, connectType, state);
//            ZLogger.d("device:" + (device != null ? device.getAddress() : "") + ", connectType=" + connectType + ", state=" + state);
//      runOnUiThread(() -> checkState());
      }

      @Override
      public void onReceiveReportLan(byte currentLanuage, byte supportedLanguage) {
        super.onReceiveReportLan(currentLanuage, supportedLanguage);
//      runOnUiThread(() -> checkState());
      }

      @Override
      public void onAckReceived(int opcode, byte status) {
        super.onAckReceived(opcode, status);
        switch (opcode) {
          case CommandContract.CMD_SET_LANGUAGE:
//                case CommandContract.CMD_SET_CONFIGURATION:
//                case CommandContract.CMD_GET_NAME:
          case CommandContract.CMD_GET_LANGUAGE:
//                case CommandContract.CMD_GET_STATUS:
//                case CommandContract.CMD_MMI:
//          runOnUiThread(() -> reload());
            break;
          default:
            break;
        }
      }
    });
    mBeeProManager.startService();

//    if (Constants.FLAVOR_HOTFIX.equals(BuildConfig.FLAVOR)) {
//      tvVersion.setText(BuildConfig.VERSION_NAME + "-" + BuildConfig.VERSION_CODE);
//    } else if (Constants.FLAVOR_QC.equals(BuildConfig.FLAVOR)) {
//      tvVersion.setText(BuildConfig.VERSION_NAME + "-" + BuildConfig.VERSION_CODE);
//    } else {
//      tvVersion.setText(BuildConfig.VERSION_NAME);
//    }

//    requestPermissions();

  }

  private FlutterTtsPlugin(Registrar registrar) {
    this.registrar = registrar;

//    if (this.mBeeProManager == null) {
      this.mBeeProManager = BeeProManager.getInstance(registrar.activeContext());
      this.mBeeProManager.addManagerCallback(mManagerCallback);
//    }

  }

  private void init(){
//    WriteLog.install(registrar.context(), "Tts", 2);

  }

//  private BeeProManager getBeeProManager() {
//    if (mBeeProManager == null) {
//      mBeeProManager = BeeProManager.getInstance(registrar.activeContext());
//      mBeeProManager.addManagerCallback(mManagerCallback);
//    }
//    return mBeeProManager;
//  }

  private BumblebeeCallback mManagerCallback = new BumblebeeCallback() {
    @Override
    public void onServiceConnectionStateChanged(boolean status, BumblebeeTool bbpro) {
      super.onServiceConnectionStateChanged(status, bbpro);
      ZLogger.d("status=" + status);
//      runOnUiThread(() -> checkState());
    }

    @Override
    public void onConnectionStateChanged(BluetoothDevice device, int connectType, int state) {
      super.onConnectionStateChanged(device, connectType, state);
//            ZLogger.d("device:" + (device != null ? device.getAddress() : "") + ", connectType=" + connectType + ", state=" + state);
//      runOnUiThread(() -> checkState());
    }

    @Override
    public void onReceiveReportLan(byte currentLanuage, byte supportedLanguage) {
      super.onReceiveReportLan(currentLanuage, supportedLanguage);
//      runOnUiThread(() -> checkState());
    }

    @Override
    public void onAckReceived(int opcode, byte status) {
      super.onAckReceived(opcode, status);
      switch (opcode) {
        case CommandContract.CMD_SET_LANGUAGE:
//                case CommandContract.CMD_SET_CONFIGURATION:
//                case CommandContract.CMD_GET_NAME:
        case CommandContract.CMD_GET_LANGUAGE:
//                case CommandContract.CMD_GET_STATUS:
//                case CommandContract.CMD_MMI:
//          runOnUiThread(() -> reload());
          break;
        default:
          break;
      }
    }
  };



  public boolean isBackpressInterrupted() {
    return false;
  }

//  public boolean onKeyDown(int var1, KeyEvent var2) {
//    if (this.isBackpressInterrupted() && var1 == 4 && var2.getAction() == 0) {
//      AlertDialog.Builder var3 = new AlertDialog.Builder(registrar.context());
//      var3.setMessage(string.rtk_message_exit_app);
//      var3.setPositiveButton(string.rtk_ok, (var1x, var2x) -> {
//        registrar.activity().finish();
//      });
//      var3.setNegativeButton(string.rtk_cancel, (DialogInterface.OnClickListener)null);
//      var3.create();
//      var3.show();
//    }
//
//    return super.onKeyDown(var1, var2);
//  }

//  protected void onActivityResult(int var1, int var2, Intent var3) {
//    switch(var1) {
//      case 32:
//        this.requestPermissions();
//      case 33:
//      case 34:
//      case 35:
//      default:
//        break;
//      case 36:
//        if (var2 == -1) {
//          BluetoothDevice var4 = (BluetoothDevice)var3.getParcelableExtra("device");
//          byte[] var5 = var3.getByteArrayExtra("scanRecord");
//          SpecScanRecord var6 = SpecScanRecord.parseFromBytes(var5);
//          if (var4 != null) {
//            this.onBtScannerCallback(var4, var6);
//          }
//        }
//    }
//
//    super.onActivityResult(var1, var2, var3);
//  }

  public void onBtScannerCallback(BluetoothDevice var1, SpecScanRecord var2) {
  }

  public void onPermissionsGranted() {
  }

//  public void onPermissionsInsufficient() {
//    AlertDialog var1 = (new Builder(this)).setMessage(this.getString(string.rtksdk_permission_denied, new Object[]{""})).setPositiveButton(string.rtksdk_permission_ok, new DialogInterface.OnClickListener() {
//      public void onClick(DialogInterface var1, int var2) {
//        var1.dismiss();
//        BaseActivity.this.redirect2AndroidDetailsSettings();
//      }
//    }).setNegativeButton(string.rtksdk_permission_cancel, new DialogInterface.OnClickListener() {
//      public void onClick(DialogInterface var1, int var2) {
//        var1.dismiss();
//        BaseActivity.this.finish();
//      }
//    }).create();
//    var1.show();
//  }

  public ArrayList<String> getRequestPermissions() {
    ArrayList var1 = new ArrayList();
    var1.add("android.permission.ACCESS_COARSE_LOCATION");
    var1.add("android.permission.ACCESS_FINE_LOCATION");
    var1.add("android.permission.READ_PHONE_STATE");
    var1.add("android.permission.READ_EXTERNAL_STORAGE");
    var1.add("android.permission.WRITE_EXTERNAL_STORAGE");
    return var1;
  }

//  public void requestPermissions() {
//    ArrayList var1 = this.getRequestPermissions();
//    ArrayList var2 = new ArrayList();
//    Iterator var3 = var1.iterator();
//
//    while(var3.hasNext()) {
//      String var4 = (String)var3.next();
//      if (ActivityCompat.checkSelfPermission(this, var4) != 0) {
//        ZLogger.d(String.format("权限[%s]未开启", var4));
//        var2.add(var4);
//      }
//    }
//
//    int var6 = var2.size();
//    if (var6 > 0) {
//      String[] var7 = new String[var6];
//
//      for(int var5 = 0; var5 < var6; ++var5) {
//        var7[var5] = (String)var2.get(var5);
//      }
//
//      ActivityCompat.requestPermissions(registrar.activity(), var7, 34);
//    } else {
//      this.onPermissionsGranted();
//    }
//
//  }



  public boolean verifyPermissions(int[] var1) {
    if (var1.length < 1) {
      return false;
    } else {
      int[] var2 = var1;
      int var3 = var1.length;

      for(int var4 = 0; var4 < var3; ++var4) {
        int var5 = var2[var4];
        if (var5 != 0) {
          return false;
        }
      }

      return true;
    }
  }

//  public void onRequestPermissionsResult(int var1, String[] var2, int[] var3) {
//    if (var1 == 34) {
//      boolean var4 = this.verifyPermissions(var3);
//      if (var4) {
//        this.onPermissionsGranted();
//      } else {
//        this.onPermissionsInsufficient();
//      }
//    } else {
//      super.onRequestPermissionsResult(var1, var2, var3);
//    }
//
//  }
//
//  public void redirect2EnableBT() {
//    Intent var1 = new Intent("android.bluetooth.adapter.action.REQUEST_ENABLE");
//    this.startActivityForResult(var1, 35);
//  }
//
//  public void redirect2AndroidDetailsSettings() {
//    try {
//      Intent var1 = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
//      var1.setData(Uri.parse("package:" + this.getPackageName()));
//      this.startActivityForResult(var1, 32);
//    } catch (Exception var2) {
//      ZLogger.e(var2.toString());
//    }
//
//  }

//  public void redirect2AndroidDetailsSettings(String var1) {
//    try {
//      Intent var2 = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
//      var2.setData(Uri.parse("package:" + var1));
//      this.startActivityForResult(var2, 32);
//    } catch (Exception var3) {
//      ZLogger.e(var3.toString());
//    }
//
//  }
//
//  public void redirect2AndroidBluetoothSettings() {
//    try {
//      Intent var1 = new Intent("android.settings.BLUETOOTH_SETTINGS");
//      this.startActivityForResult(var1, 33);
//    } catch (Exception var2) {
//      ZLogger.e(var2.toString());
//    }
//
//  }

  @Override
  public void onMethodCall(MethodCall call, Result result) {
    if (call.method.equals("getPlatformVersion")) {
      result.success("Android " + android.os.Build.VERSION.RELEASE);
    } else {
      result.notImplemented();
    }
  }

}
