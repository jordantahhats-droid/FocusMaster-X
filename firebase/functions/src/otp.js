const crypto = require('crypto');
const bcrypt = require('bcryptjs');
const admin = require('firebase-admin');
const functions = require('firebase-functions');

admin.initializeApp();

exports.generateAccountSwitchOtp = functions.https.onCall(async (data, context) => {
  if (!context.auth) throw new functions.https.HttpsError('unauthenticated', 'Sign in required.');
  const { studentId } = data;
  const studentRef = admin.firestore().collection('users').doc(studentId);
  const student = await studentRef.get();
  if (!student.exists || student.data().linkedAdminId !== context.auth.uid) {
    throw new functions.https.HttpsError('permission-denied', 'Only the linked admin can approve this request.');
  }
  const otp = String(crypto.randomInt(100000, 1000000));
  const hashedOTP = await bcrypt.hash(otp, 12);
  await admin.firestore().collection('otpRequests').doc(studentId).set({
    studentId,
    adminId: context.auth.uid,
    hashedOTP,
    createdAt: admin.firestore.FieldValue.serverTimestamp(),
    attempts: 0,
  });
  return { otp, expiresInSeconds: 120 };
});

exports.verifyAccountSwitchOtp = functions.https.onCall(async (data, context) => {
  if (!context.auth) throw new functions.https.HttpsError('unauthenticated', 'Sign in required.');
  const { otp } = data;
  const requestRef = admin.firestore().collection('otpRequests').doc(context.auth.uid);
  return admin.firestore().runTransaction(async (tx) => {
    const request = await tx.get(requestRef);
    if (!request.exists) throw new functions.https.HttpsError('not-found', 'No active OTP request.');
    const payload = request.data();
    const createdAt = payload.createdAt.toMillis();
    if (Date.now() - createdAt > 120000) {
      tx.delete(requestRef);
      throw new functions.https.HttpsError('deadline-exceeded', 'OTP expired.');
    }
    const valid = await bcrypt.compare(String(otp), payload.hashedOTP);
    if (!valid) {
      const attempts = (payload.attempts || 0) + 1;
      attempts >= 3 ? tx.delete(requestRef) : tx.update(requestRef, { attempts });
      throw new functions.https.HttpsError('permission-denied', 'Invalid OTP.');
    }
    tx.update(admin.firestore().collection('users').doc(context.auth.uid), { accountType: 'INDIVIDUAL' });
    tx.delete(requestRef);
    return { accountType: 'INDIVIDUAL' };
  });
});
