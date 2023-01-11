import styles from './UserInfoForm.module.css';
import { LabelTextInput, TextInput } from '../Input';
import { useState, useEffect } from 'react';
import { ButtonNightBlue } from '../Button';

const initialUserInfo = {
  userId: 0,
  nickName: '',
  email: 'test@gmail.com',
  password: '',
  passwordConfirm: '',
  phoneNumner: '',
  loginType: '',
  userStatus: 'TUTEE',
  createAt: '',
  updateAt: '',
  secondPassword: '',
  secondPasswordConfirm: '',
};

const UserInfoForm = () => {
  const [userInfoData, setUserInfoData] = useState(initialUserInfo);
  const [userStatus, setUserStatus] = useState('');
  const [confirmPassword, setConfirmPassword] = useState(false);

  const inputHandler = (e) => {
    const { id, value } = e.target;
    setUserInfoData({ ...userInfoData, [id]: value });
  };

  const radioHandler = (e) => {
    setUserStatus(e.target.value);
  };

  //비밀번호와 비밀번호 확인 체크하는 핸들러 함수
  const confirmHandler = () => {
    if (userInfoData.password === userInfoData.passwordConfirm) {
      console.log('같은데!');
      setConfirmPassword(true);
    } else {
      console.log('다른데!');
      setConfirmPassword(false);
    }
  };

  //2차 비밀번호까지 확인하려면 다른 방법을 사용해야 할 것 같기도 함...
  useEffect(() => {
    confirmHandler();
  }, [userInfoData.passwordConfirm, userInfoData.password]);

  return (
    <div className={styles.userInfoContainer}>
      <span className={styles.userInfoText}>회원 정보 수정</span>
      <div className={styles.inputArea}>
        <LabelTextInput
          id="nickName"
          name="닉네임"
          placeHolder="닉네임"
          type="text"
          value={userInfoData.nickName}
          handler={inputHandler}
        />
        <span>닉네임은 최소 2글자 이상이어야 합니다.</span>
        <div className={styles.emailBox}>
          <span>이메일</span>
          <span>{userInfoData.email}</span>
        </div>
        <LabelTextInput
          id="password"
          name="비밀번호 수정"
          placeHolder="비밀번호 수정"
          type="password"
          value={userInfoData.password}
          handler={inputHandler}
        />
        <TextInput
          id="passwordConfirm"
          type="password"
          placeHolder="비밀번호 확인"
          value={userInfoData.passwordConfirm}
          handler={inputHandler}
        />
        <span>비밀번호 입력이 잘못되었습니다.</span>
        <LabelTextInput
          id="phoneNumebr"
          name="휴대폰 번호"
          placeHolder="휴대폰 번호"
          type="tel"
          value={userInfoData.phoneNumner}
          handler={inputHandler}
        />
        <span>올바르지 않은 형식입니다.</span>

        <div className={styles.userStatusBox}>
          <span className={styles.userType}>유저 타입</span>
          <div className={styles.radioBox}>
            <label
              className={
                userStatus === '튜터' ? styles.checkedLabel : styles.normalLabel
              }
            >
              <input
                type="radio"
                value="튜터"
                checked={userStatus === '튜터'}
                onChange={radioHandler}
                className={styles.radioButton}
              ></input>
              튜터
            </label>
            <label
              className={
                userStatus === '튜티' ? styles.checkedLabel : styles.normalLabel
              }
            >
              <input
                type="radio"
                value="튜티"
                checked={userStatus === '튜티'}
                onChange={radioHandler}
                className={styles.radioButton}
              ></input>
              튜티
            </label>
          </div>
        </div>
        <span>유저 타입은 최초 1회 결정 후 수정할 수 없습니다.</span>

        <LabelTextInput
          id="secondPassword"
          name="2차 비밀번호"
          placeHolder="2차 비밀번호"
          type="password"
          value={userInfoData.secondPassword}
          handler={inputHandler}
        />
        <TextInput
          id="secondPasswordConfirm"
          type="password"
          placeHolder="2차 비밀번호 확인"
          value={userInfoData.secondPasswordConfirm}
          handler={inputHandler}
        />
        <span>2차 비밀번호는 프로필 관리에 사용됩니다.</span>
      </div>
      <ButtonNightBlue text="수정 완료" />
    </div>
  );
};

export default UserInfoForm;
