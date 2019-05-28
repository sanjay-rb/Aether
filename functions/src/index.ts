import * as functions from 'firebase-functions';
import * as admin from 'firebase-admin';

admin.initializeApp(functions.config().firebase)

export const addTimeStamp = functions.database
.ref('/AirRealTimeData/value')
.onUpdate((snapshot) => {
    const sensor_value = snapshot.after.val();

    const timestamp = new Date().getTime();
    console.log(`Value : ${sensor_value} Time: ${timestamp}`);

    admin.database().refFromURL("https://airwater-79c5e.firebaseio.com/AirDBTimeStamp/" + timestamp).set(sensor_value).then(res=>{
        console.log(`Successfully Updated Value = ${sensor_value} time = ${timestamp}`, res);
    }).catch(err =>{
        console.log('Error in Update:', err);
    });

    return Promise.resolve(sensor_value);
});
