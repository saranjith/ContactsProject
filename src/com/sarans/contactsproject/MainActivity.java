package com.sarans.contactsproject;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {
	public static final int PICK_CONTACT = 1;
	private Button btnContacts;
	private TextView txtContacts;
	private static final int EMAIL_REQUEST = 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		btnContacts = (Button) findViewById(R.id.btn_contacts);
		txtContacts = (TextView) findViewById(R.id.txt_contacts);

		btnContacts.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(Intent.ACTION_PICK);
				intent.setData(ContactsContract.CommonDataKinds.Email.CONTENT_URI);
				startActivityForResult(intent, EMAIL_REQUEST);

			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			switch (requestCode) {
			case EMAIL_REQUEST:
				Uri selectedUri = data.getData();
				ContentResolver cr = getContentResolver();
				Cursor c = cr.query(selectedUri, new String[] { ContactsContract.CommonDataKinds.Identity.DISPLAY_NAME, ContactsContract.CommonDataKinds.Email.DATA }, null, null, null);
				c.moveToFirst();
				String name = c.getString(c.getColumnIndex(ContactsContract.Data.DISPLAY_NAME));
				String email = c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));

				txtContacts.setText(name + ", " + email);
				break;
			}
		}
	}
}
