<h2>Target: Set ringtone for each phone number according to property (platform: Aindroid 6)</h2>

Reference Android 6.0 Contacts app source code

/packages/apps/Contacts/src/com/android/contacts/editor/ContactEditorBaseFragment.java


------------	


	doPickRingtone() 

->  

	startActivityForResult(intent, REQUEST_CODE_PICK_RINGTONE);
	//require to show ringtone list

->

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
	...
				case REQUEST_CODE_PICK_RINGTONE: {
					if (data != null) {
						final Uri pickedUri = data.getParcelableExtra(
								RingtoneManager.EXTRA_RINGTONE_PICKED_URI);
						onRingtonePicked(pickedUri);
					}
					break;
				}
	...
	}
->

    private void onRingtonePicked(Uri pickedUri) {
        if (pickedUri == null || RingtoneManager.isDefault(pickedUri)) {
            mCustomRingtone = null;
        } else {
            mCustomRingtone = pickedUri.toString();
        }
        Intent intent = ContactSaveService.createSetRingtone(
                mContext, mLookupUri, mCustomRingtone);
        mContext.startService(intent);
    }

->

	ContactSaveService.createSetRingtone
	
    public static Intent createSetRingtone(Context context, Uri contactUri,
            String value) {
        Intent serviceIntent = new Intent(context, ContactSaveService.class);
        serviceIntent.setAction(ContactSaveService.ACTION_SET_RINGTONE);
        serviceIntent.putExtra(ContactSaveService.EXTRA_CONTACT_URI, contactUri);
        serviceIntent.putExtra(ContactSaveService.EXTRA_CUSTOM_RINGTONE, value);

        return serviceIntent;
    }

    private void setRingtone(Intent intent) {
        Uri contactUri = intent.getParcelableExtra(EXTRA_CONTACT_URI);
        String value = intent.getStringExtra(EXTRA_CUSTOM_RINGTONE);
        if (contactUri == null) {
            Log.e(TAG, "Invalid arguments for setRingtone");
            return;
        }
        ContentValues values = new ContentValues(1);
        values.put(Contacts.CUSTOM_RINGTONE, value);
        getContentResolver().update(contactUri, values, null, null);
    }

------------	

So, if we want to set ringtone for each phoneNumber we can use "getContentResolver().update(contactUri, values, null, null);"


