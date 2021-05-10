package com.dailyapps.biometricauthentication

import android.Manifest
import android.app.KeyguardManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import androidx.biometric.BiometricPrompt
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.dailyapps.biometricauthentication.databinding.ActivityMainBinding
import java.util.concurrent.Executor

class MainActivity : AppCompatActivity() {
    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        executor = ContextCompat.getMainExecutor(this)

        biometricPrompt = BiometricPrompt(
            this@MainActivity,
            executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    binding.tvText.text = "Authentication Error: $errString"
                    Toast.makeText(
                        this@MainActivity,
                        "Authentication Error: $errString",
                        Toast.LENGTH_LONG
                    ).show()
                }

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    binding.tvText.text = "Authentication Succed: $result"
                    Toast.makeText(
                        this@MainActivity,
                        "Authentication Succed: $result",
                        Toast.LENGTH_LONG
                    ).show()
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    binding.tvText.text = "Authentication Failed"
                    Toast.makeText(this@MainActivity, "Authentication Failed", Toast.LENGTH_LONG)
                        .show()
                }
            })

        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Biometric Authentication")
            .setSubtitle("Login using fingerprint authentication")
            .setNegativeButtonText("Use App Password insteas")
            .build()

        binding.btnAuthentication.setOnClickListener {
            biometricPrompt.authenticate(promptInfo)
        }
    }
}