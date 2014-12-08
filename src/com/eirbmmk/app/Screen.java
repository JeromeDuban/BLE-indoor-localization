package com.eirbmmk.app;

/**
 * Enumeration of the different screens which can be displayed. For each screen item, the package
 * name where the screen's implementation is located, the prefix used to name the classes declared
 * in this package, the title of the screen, and the drawable resource icon name are associated.
 */
public enum Screen {
    CLUBS("clubs", "Clubs", "Clubs et Assos", "ic_clubs"),
    CONFIGURATION("configuration", "Configuration", "Email & Wi-Fi", "ic_wifi"),
    CONNECTION("connection", "Connection", "Connexion", "ic_connection"),
    EVENTS("events", "Events", "Evènements", "ic_events"),
    LOGOUT("logout", "Logout", "Déconnexion", "ic_connection"),
    MAP("map", "Map", "Plan", "ic_map"),
    NEWS("news", "News", "Actualités", "ic_news"),
    PROFILES("profiles", "Profiles", "Profils", "ic_profile"),
    SCHEDULE("schedule", "Schedule", "Emploi du temps", "ic_schedule"),
    SETTINGS("settings", "Settings", "Réglages", "ic_settings");

    /**
     * The package's name including the corresponding screen's implementation.
     */
    private String mPackage;

    /**
     * The prefix' used to name the classes declared in the corresponding package.
     */
    private String mClassPrefix;

    /**
     * Title of the screen
     */
    private String mTitle;

    /**
     * Drawable Icon name resource associated to the screen
     *
     */
    private String mIconName;

    /**
     * Constructor of the Screen enum.
     * The packageSuffix parameter corresponds to the last word of the package containing the screen
     * implementation corresponding to the screen enum to create.
     * For example, for a connection screen implemented in the "com.eirbmmk.app.connection",
     * the packageSuffix referred to "connection".
     *
     * The classPrefix parameter corresponds to the prefix used to name the classes declared in the
     * corresponding package. Usually, its the package suffix with a first capital letter.
     * For instance, the classes declared in the "com.eirbmmk.app.connection" package are named
     * "Connection" followed by the name of the class like "ConnectionController" or "ConnectionModel".

     *
     * @param packageSuffix the last word of the package including the corresponding scree implementation.
     * @param classPrefix the prefix used in classes' name declared in the package.
     * @param iconName the drawable resource name corresponding to the icon to associate to the screen
     * @param title the title of the screen which will be displayed
     */
    Screen(String packageSuffix, String classPrefix, String title, String iconName)
    {
        mPackage = this.getClass().getPackage().getName() + "." + packageSuffix;
        mClassPrefix = classPrefix;
        mTitle = title;
        mIconName = iconName;
    }

    /**
     * This method returns the package name associated to the corresponding screen (e.g. the package
     * name where the screen's implementation is located).
     *
     * @return the package name of the corresponding screen.
     */
    public String getPackage()
    {
        return mPackage;
    }

    /**
     * This method returns the prefix used to name the classes declared in the package corresponding
     * to the screen.
     *
     * @return the prefix used.
     */
    public String getClassPrefix()
    {
        return mClassPrefix;
    }

    /**
     * This method returns the title associated to the screen
     *
     * @return the title of the screen
     */
    public String getTitle()
    {
        return mTitle;
    }

    /**
     * this method returns the name of the drawable resource corresponding to the icon
     * associated to the screen.
     *
     * @return the icon name
     */
    public String getIconName()
    {
        return mIconName;
    }
}
