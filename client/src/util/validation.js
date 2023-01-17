const validation = (value, type) => {
  const emailExp =
    /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/;

  const passwordExp = /^(?=.*\d)(?=.*[a-zA-Z])[0-9a-zA-Z]{8,16}$/;

  switch (type) {
    case 'email':
      return emailExp.test(value);
    case 'password':
      return passwordExp.test(value);
    default:
      return;
  }
};

export default validation;
