const getStringBytes = (str) => {
  let bytes = 0;

  for (let k of str) {
    if (encodeURI(k).length > 4) bytes += 2;
    else bytes++;
  }

  return bytes;
};

const validation = (value, type) => {
  const emailExp =
    /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/;
  const passwordExp = /^(?=.*\d)(?=.*[a-zA-Z])[0-9a-zA-Z]{8,16}$/;
  const secondPasswordExp = /^[0-9]{4,8}$/;
  const phoneExp = /^01([0|1|6|7|8|9])([0-9]{3,4})([0-9]{4})$/;
  const phoneExpHyphen = /^01([0|1|6|7|8|9])-?([0-9]{3,4})-?([0-9]{4})$/;

  switch (type) {
    case 'email':
      return emailExp.test(value);
    case 'password':
      return passwordExp.test(value);
    case 'secondPassword':
      return secondPasswordExp.test(value);
    case 'name':
      return 4 <= getStringBytes(value) && getStringBytes(value) <= 12;
    case 'phoneNumber':
      return phoneExp.test(value) || phoneExpHyphen.test(value);
    default:
      return;
  }
};

export default validation;
