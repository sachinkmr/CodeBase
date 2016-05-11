private static CookieStore seleniumCookiesToCookieStore() {

    Set<Cookie> seleniumCookies = WebDriverWrapper.getDriver().manage().getCookies();
    CookieStore cookieStore = new BasicCookieStore();

    for (Cookie seleniumCookie : seleniumCookies) {
        BasicClientCookie basicClientCookie =
                new BasicClientCookie(seleniumCookie.getName(), seleniumCookie.getValue());
        basicClientCookie.setDomain(seleniumCookie.getDomain());
        basicClientCookie.setExpiryDate(seleniumCookie.getExpiry());
        basicClientCookie.setPath(seleniumCookie.getPath());
        cookieStore.addCookie(basicClientCookie);
    }

}