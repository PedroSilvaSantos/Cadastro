package br.com.caelum.cadastro.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.telephony.SmsMessage;

import br.com.caelum.cadastro.R;
import br.com.caelum.cadastro.dao.AlunoDAO;

/**
 * Created by android6406 on 23/06/16.
 */
public class SMSReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        AlunoDAO dao = new AlunoDAO(context);
        Bundle bundle =intent.getExtras();
        Object[] mensagens = (Object[])bundle.get("pdus");
        byte[] mensagem = (byte[]) mensagens[0];
        String formato = (String)bundle.get("format");
        SmsMessage sms = SmsMessage.createFromPdu(mensagem,formato);

        if(dao.isAluno(sms.getDisplayOriginatingAddress())){
            MediaPlayer mp = MediaPlayer.create(context, R.raw.msg);
            dao.close();
            mp.start();
        }


    }

}
