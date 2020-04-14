package com.epicshaggy.biometric;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.biometric.BiometricPrompt;

import android.util.Log;
import android.view.View;

import com.epicshaggy.biometric.capacitornativebiometric.R;

import java.util.concurrent.Executor;

public class AuthAcitivy extends AppCompatActivity {

    private Executor executor;
    private BiometricPrompt.PromptInfo promptInfo;
    private BiometricPrompt biometricPrompt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth_acitivy);

        executor = this.getMainExecutor();

        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Log in")
                .setNegativeButtonText("Cancel")
                .build();

        biometricPrompt = new BiometricPrompt(this, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                Log.i("bio", "Error");
                finishActivity("error");
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                Log.i("bio", "success");
                finishActivity("success");
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                Log.i("bio", "failed");
                finishActivity("failed");
            }
        });

        biometricPrompt.authenticate(promptInfo);

    }

    void finishActivity(String result) {
        Intent intent = new Intent();
        intent.putExtra("result", result);
        setResult(RESULT_OK, intent);
        finish();
    }

}
