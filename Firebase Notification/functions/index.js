const functions = require("firebase-functions");
const admin = require('firebase-admin');

admin.initializeApp(functions.config().firebase);

exports.PushNotification = functions.firestore.document('Request/{uid}').onWrite( (event) => {
    let title = event.after.get('name');
    let content = event.after.get('status');
    
    admin.firestore().collection("UserTokens").get().then(
        result => {
            var registrationTokens = [];
            result.docs.forEach(
                tokenDocument => {
                    registrationTokens.push(tokenDocument.data().token);
                }
            );
            admin.messaging().sendMulticast(
                {
                    tokens: registrationTokens,
                    notification: {
                        title: title,
                        body: content
                    }
                }

            );

            
        }    
    )
    
   
    


});