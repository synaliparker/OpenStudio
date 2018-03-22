package edu.mills.openstudio;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * Monitor if the user is logged into their account.
 */
class SessionManager {
    private static final String PREF_NAME = "OpenStudioLogin";
    private static final String KEY_IS_LOGGEDIN = "isLoggedIn";
    private SharedPreferences pref;
    private Editor editor;
    private Context _context;
    private int PRIVATE_MODE = 0;

    /**
     * Constructor for the class.
     * @param context login object
     */
    SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    /**
     * Set new login status
     * @param isLoggedIn true if user is logged in, false if user is not logged in
     */
    void setLogin(boolean isLoggedIn) {

        editor.putBoolean(KEY_IS_LOGGEDIN, isLoggedIn);
        editor.commit();
    }

    /**
     * Check if the user is logged in.
     * @return stored value saved in shared preferences
     */

    boolean isLoggedIn(){
        return pref.getBoolean(KEY_IS_LOGGEDIN, false);
    }
}
