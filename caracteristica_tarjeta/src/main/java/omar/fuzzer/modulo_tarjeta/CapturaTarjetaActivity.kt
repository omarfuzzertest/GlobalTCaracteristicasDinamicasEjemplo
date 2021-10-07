package omar.fuzzer.modulo_tarjeta

import android.R.attr
import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.card.payment.CardIOActivity
import io.card.payment.CreditCard


class CapturaTarjetaActivity : AppCompatActivity() {
    private val CARD_REQUEST = 31
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
      //  setContentView(R.layout.activity_captura_tarjeta)
        tomarTarjeta()
    }

    private fun tomarTarjeta() {
        val scanIntent = Intent(this, CardIOActivity::class.java)
            .putExtra(CardIOActivity.EXTRA_NO_CAMERA, false)
            .putExtra(CardIOActivity.EXTRA_REQUIRE_EXPIRY, true)
            .putExtra(CardIOActivity.EXTRA_SCAN_EXPIRY, true)
            .putExtra(CardIOActivity.EXTRA_REQUIRE_CVV, false)
            .putExtra(CardIOActivity.EXTRA_REQUIRE_POSTAL_CODE, true)
            .putExtra(CardIOActivity.EXTRA_RESTRICT_POSTAL_CODE_TO_NUMERIC_ONLY, true)
            .putExtra(CardIOActivity.EXTRA_REQUIRE_CARDHOLDER_NAME, true)
            .putExtra(CardIOActivity.EXTRA_SUPPRESS_MANUAL_ENTRY, true)
            .putExtra(CardIOActivity.EXTRA_USE_CARDIO_LOGO, false)
            .putExtra(CardIOActivity.EXTRA_HIDE_CARDIO_LOGO, true)
            //.putExtra(CardIOActivity.EXTRA_LANGUAGE_OR_LOCALE, (String) mLanguageSpinner.getSelectedItem())
            .putExtra(CardIOActivity.EXTRA_USE_PAYPAL_ACTIONBAR_ICON, false)
            .putExtra(CardIOActivity.EXTRA_KEEP_APPLICATION_THEME, false)
            .putExtra(CardIOActivity.EXTRA_GUIDE_COLOR, Color.GREEN)
            .putExtra(CardIOActivity.EXTRA_SUPPRESS_CONFIRMATION, true)
            .putExtra(CardIOActivity.EXTRA_SUPPRESS_SCAN,false)
            .putExtra(CardIOActivity.EXTRA_RETURN_CARD_IMAGE, false)

        startActivityForResult(scanIntent, CARD_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            CARD_REQUEST -> {
                    if (data != null && data.hasExtra(CardIOActivity.EXTRA_SCAN_RESULT)){
                        val scanResult: CreditCard = data.getParcelableExtra(CardIOActivity.EXTRA_SCAN_RESULT)!!

                        val resultIntent = Intent()
                        resultIntent.putExtra( "cardNumber",scanResult.cardNumber)
                        resultIntent.putExtra( "redactedCardNumber",scanResult.redactedCardNumber)
                        setResult(RESULT_OK, resultIntent)
                        finish()
                    }
                    else {
                        setResult(Activity.RESULT_CANCELED)
                        finish()
                    }
            }
        }
    }
}