package com.checkmate.matches.model.rabbitmq;

public enum UserEventType {
    USER_REGISTRATION,
    REMOVE_USER_COINS,
    ADD_USER_COINS,

    USER_LOGIN,
    USER_LOGOUT,
    PROFILE_UPDATE,
    PASSWORD_CHANGE,
    PASSWORD_RESET_REQUEST,
    EMAIL_VERIFICATION,
    ACCOUNT_DEACTIVATION,
    ACCOUNT_DELETION,
    FAILED_LOGIN_ATTEMPT,
    USER_ROLE_CHANGE,
    TWO_FACTOR_AUTHENTICATION_SETUP,
    SUBSCRIPTION_CHANGE,
    PAYMENT_INFO_UPDATE,
    SERVICE_USAGE_EVENT,
    NOTIFICATION_PREFERENCES_UPDATE,
    PRIVACY_SETTINGS_UPDATE,
    SECURITY_ALERT,
    FEEDBACK_SUBMISSION,
    SOCIAL_MEDIA_LINKING,
    LEGAL_AGREEMENT_CONSENT,

}
