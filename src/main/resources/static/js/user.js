const URL_REST_ROOT = "/user";

const sendGET = (uid) => {
    sendAjax("GET", `${URL_REST_ROOT}/${uid}`, null,
        simpleSuccessCallback, simpleErrorCallback);
};

const sendGETWithUrl = (uid) => {
    window.open(`${URL_REST_ROOT}/${uid}`);
};

const sendGETWithParams = () => {
    const prop = getUserPropertyByPrompt(
        "LastName? (partial match)", "",
        "FastName? (partial match)", "",
        "Birthday? (full match)", "");
    if (prop == null) {
        return;
    }

    const urlParams = buildUrlParamsFromUserProperty(prop);

    sendAjax("GET", URL_REST_ROOT + urlParams, null,
        simpleSuccessCallback, simpleErrorCallback);
};

const sendGETWithUrlParams = () => {
    const prop = getUserPropertyByPrompt(
        "LastName? (partial match)", "",
        "FastName? (partial match)", "",
        "Birthday? (full match)", "");
    if (prop == null) {
        return;
    }

    const urlParams = buildUrlParamsFromUserProperty(prop);

    window.open(URL_REST_ROOT + urlParams);
};

const sendPOST = () => {
    const prop = getUserPropertyByPrompt(
        "LastName?", "",
        "FastName?", "",
        "Birthday?", todayString());
    if (prop == null) {
        return;
    }

    const jsonData = JSON.stringify({
            lastName: prop.lastName,
            firstName: prop.firstName,
            birthday: prop.birthday
        });

    sendAjax("POST", URL_REST_ROOT, jsonData,
        successCallbackWithReload, simpleErrorCallback);
};

const sendPUT = (uid, lastName, firstName, birthday) => {
    const prop = getUserPropertyByPrompt(
        "New LastName?", nullToEmptyString(lastName),
        "New FastName?", nullToEmptyString(firstName),
        "New Birthday?", nullToEmptyString(birthday));
    if (prop == null) {
        return;
    }

    const jsonData = JSON.stringify({
        uid : uid,
        lastName: prop.lastName,
        firstName: prop.firstName,
        birthday: prop.birthday
    });

    sendAjax("PUT", `${URL_REST_ROOT}/${uid}`, jsonData,
        successCallbackWithReload, simpleErrorCallback);
};

const sendDELETE = (uid) => {
    if (!confirm(`Delete user ${uid} ?`)) {
        return;
    }

    sendAjax("DELETE", `${URL_REST_ROOT}/${uid}`, null,
        successCallbackWithReload, simpleErrorCallback);
};

const sendAjax = (type, url, data, onSuccess, onError) => {
    $.ajax({
        type : type,
        url : url,
        data : data,
        contentType : "application/JSON",
        scriptCharset : "utf-8",
        success : onSuccess,
        error : onError
    });
};

const simpleSuccessCallback = (res) => {
    simpleAlertCallback("Success", res);
};

const successCallbackWithReload = (res) => {
    simpleAlertCallback("Success", res);
    location.reload();
};

const simpleErrorCallback = (res) => {
    simpleAlertCallback("Error", res);
};

const simpleAlertCallback = (msg, res) => {
    alert(msg);
    if (res != null && res != "") {
        alert(JSON.stringify(res, null, "\t"));
    }
};

const getUserPropertyByPrompt = (lastNameMsg, lastNameIniVal, firstNameMsg, firstNameIniVal,
    birthdayMsg, birthdayIniVal) => {
    const lastName = prompt(lastNameMsg, lastNameIniVal);
    if (lastName == null) {
        return null;
    }
    const firstName = prompt(firstNameMsg, firstNameIniVal);
    if (firstName == null) {
        return null;
    }
    const birthday = prompt(birthdayMsg, birthdayIniVal);
    if (birthday == null) {
        return null;
    }
    return new UserProperty(lastName, firstName, birthday);
};

const buildUrlParamsFromUserProperty = (prop) => {
    let urlParams = "?";
    if (prop.lastName != "") {
        urlParams += `lastName=${prop.lastName}`;
    }
    if (prop.firstName != "") {
        urlParams += `firstName=${prop.firstName}`;
    }
    if (prop.birthday != "") {
        urlParams += `birthday=${prop.birthday}`;
    }
    return urlParams;
};

const todayString = () => {
    return new Date().toISOString().substring(0, 10)
};

const nullToEmptyString = (value) => {
    return value == null ? "" : value;
};

class UserProperty {
    constructor(lastName, firstName, birthday) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.birthday = birthday;
    }
}