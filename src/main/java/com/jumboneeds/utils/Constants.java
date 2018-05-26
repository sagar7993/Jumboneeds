package com.jumboneeds.utils;

public class Constants {

    public static final String MIX_PANEL_API_TOKEN = "f634f2ae0fbb56ecbd2713d6e32724ca";

    static final String FCM_KEY = "AIzaSyBn4Y1wXHAg8fYWVfXJ4DXojOEVqM3AgPg";

    public static final String STORE_PATH = "/home/ubuntu/store/";

    public static final String STORE_PATH_FINANCE = "/home/ubuntu/store-finance/";

    public static final String DATABASE_DUMP_PATH = "/home/ubuntu/dump/";

    public static final String WINDOWS_PRODUCT_FILE_PATH = "C:\\wamp64\\www\\jumboneeds\\builds\\admin\\images\\product\\";
    public static final String UBUNTU_PRODUCT_FILE_PATH = "/var/www/html/admin/images/product/";

    public static final String LOCAL_PRODUCT_IMAGE_URL = "http://localhost/jumboneeds/builds/admin/images/product/";
    public static final String JUMBONEEDS_PRODUCT_IMAGE_URL = "http://jumboneeds.com/admin/images/product/";

    //Email Strings
    public static final String SUPPORT_MAIL = "support@jumboneeds.com";
    public static final String TECH_EMAIL = "tech@jumboneeds.com";
    public static final String CONTACT_EMAIL = "contact@jumboneeds.com";
    public static final String OPERATIONS_EMAIL = "operaions@jumboneeds.com";
    public static final String FROM_EMAIL = SUPPORT_MAIL;
    public static final String REPLY_TO_EMAIL = SUPPORT_MAIL;

    public static final String STORE_PRINTS_SUBJECT = "Store Prints";
    public static final String SERVICE_DISCONTINUED = "Service discontinued due to low balance";

    //Success Status Message
    public static final String SIGN_UP_SUCCESSFUL  = "Signup successful";
    public static final String LOGIN_SUCCESSFUL  = "Login successful";
    public static final String BUILDING_AND_FLAT_UPDATED  = "Building and Flat updated";
    public static final String PROFILE_UPDATED  = "Profile updated";
    public static final String RECHARGE_DONE = "Recharge has been done successfully";
    public static final String SUBSCRIPTION_ADDED = "Subscription Created Successfully";
    public static final String SUBSCRIPTION_UPDATED = "Subscription Updated Successfully";
    public static final String EXCEPTION_ADDED = "Exception Created Successfully";

    //Failure Status Message
    public static final String INVALID_USER_CREDENTIALS = "Incorrect Mobile Number / Password combination";
    public static final String NOT_A_REGISTERED_USER = "Not a registered user. Please sign up to continue";
    public static final String INVALID_ADMIN_CREDENTIALS = "Incorrect Email / Password combination";
    public static final String INVALID_PARTNER_CREDENTIALS = "Invalid Partner User Name / Password combination";
    public static final String SOMETHING_WENT_WRONG = "Something went wrong. Please try again later";
    public static final String AUTHORIZATION_FAILED = "Authorization failed";
    public static final String UNREGISTERED_PARTNER = "Unregistered Partner";
    public static final String UNREGISTERED_EXECUTIVE = "Unregistered Executive";
    public static final String NON_REGISTERED_EMAIL = "Please enter valid registered email";
    public static final String REGISTERED_EMAIL = "User with entered Email already exists";
    public static final String REGISTERED_MOBILE_NUMBER = "User with entered Mobile Number already exists";
    public static final String REGISTERED_PARTNER_NAME = "Partner UserName must be unique.";
    public static final String REGISTERED_BUILDING_NAME = "Building Name must be unique.";
    public static final String PRODUCT_MASTER_EXISTS = "Product Name / Unit Combination must be unique.";
    public static final String INVALID_AMOUNT = "Amount cannot be zero.";
    public static final String USER_NOT_FOUND = "No such user found.";
    public static final String EXCEPTION_ALREADY_EXISTS = "Exception for this subscription with same date and product quantity already exists";
    public static final String SAME_SUBSCRIPTION_EXCEPTION_QUANTITY = "Quantity for exception is same as that of the subscription";
    public static final String NO_BUILDINGS_FOUND = "No Building(s) fond for Partner";
    public static final String MISSING_USERNAME_OR_PASSWORD = "Missing userName or password for Partner";
    public static final String NO_SUBSCRIPTIONS_FOUND_FOR_BUILDING = "No subscriptions found for Building";
    public static final String NO_SUBSCRIPTIONS_FOUND_FOR_DATE = "No subscriptions found for Date";

    //Transaction Logs Strings
    public static final String CASH_TOTAL = "Cash Total";
    public static final String ONLINE_TOTAL = "Online Total";
    public static final String COUPON_REFUND_TOTAL = "Coupon Refund Total";
    public static final String NON_DELIVERY_REFUND_TOTAL = "Non-Delivery Refund Total";
    public static final String OTHERS_TOTAL = "Others Total";
    public static final String TOTAL_AMOUNT = "Total Amount";

    static final int SERVER_DATE_CHANGE_SECOND = 18*60*60 + 30*60;
    public static final long START_DATE_TO_START_DATE_DIFFERENCE = 86399000; // Difference in milliseconds between 00:00:00 and 23:59:59 for any date
    public static final int UTC_TO_IST_DIFFERENCE = (5*60*60 + 30*60) * 1000; // Difference in milliseconds of 5 hours and 30 minutes for any date

    //Miscellaneous
    public static final String DEVICE_TYPE_ANDROID = "Android";
    public static final String DEVICE_TYPE_IOS = "iOS";
    public static final String COUPONS = "Coupons";
    public static final int SCHEDULE_PAST_LIMIT = 30;
    public static final int SCHEDULE_FUTURE_LIMIT = 30;

}