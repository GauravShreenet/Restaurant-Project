const functions = require("firebase-functions");
const admin = require('firebase-admin');
admin.initializeApp();

exports.androidSendNotification = functions.firestore.document('Request/{docId}').onCreate(
    (snapshot, context) => {
        admin.messaging().sendToTopic(
            "new_user_forums",
            {
                notification: {
                    title: "Resturant Order",
                    body: "New Order"
                }
            }
        );
    }
)
