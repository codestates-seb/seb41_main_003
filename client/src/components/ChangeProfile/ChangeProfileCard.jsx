import { LabelTextInput } from '../Input';
import { ButtonNightBlue } from '../Button';
import styles from './ChangeProfileCard.module.css';
import { MdMode } from 'react-icons/md';
import PropType from 'prop-types';
import SubjectsButtons from './SubjectsButtons';
import ModalState from '../../recoil/modal.js';
import { useSetRecoilState, useResetRecoilState, useRecoilValue } from 'recoil';
import axios from 'axios';
import { useNavigate, useLocation } from 'react-router-dom';
import { useState } from 'react';
import defaultUser from '../../assets/defaultUser.png';
import Profile from '../../recoil/profile';

const ChangeProfileCard = ({ isNew = true, user, setUser }) => {
  const { name, bio, school, subjects, profileStatus, profileImage } = user;
  const [imgFile, setImgFile] = useState({});
  const [imgSrc, setImgSrc] = useState();
  const navigate = useNavigate();

  const profileId = useLocation().state?.profileId;

  const setModal = useSetRecoilState(ModalState);
  const resetModal = useResetRecoilState(ModalState);
  const setProfile = useSetRecoilState(Profile);

  const profile = useRecoilValue(Profile);

  const requiredProps = {
    isOpen: true,
    modalType: 'alert',
    props: { text: '필수 입력 사항을 모두 작성해주세요.' },
  };

  const patchImg = async (id) => {
    const formData = new FormData();
    formData.append('image', imgFile);
    for (const key of formData.keys()) {
      console.log(key);
    }
    for (const value of formData.values()) {
      console.log(value);
    }
    await axios
      .patch(`/upload/profile-image/${id}`, formData, {
        headers: {
          'Content-Type': 'multipart/form-data',
        },
      })
      .then(({ data }) => {
        const profileImage = data.data[0];
        console.log(profileImage);
        console.log('성공!');

        if (profile.profileId === profileId) {
          setProfile((prev) => ({
            ...prev,
            url: profileImage.url,
          }));
        }
      })
      .catch(({ response }) => {
        console.log(response);
        console.log(response.status);
        console.log(response.data.message);
      });
  };

  const patchProfile = async () => {
    await axios
      .patch(`/profiles/details/${profileId}`, {
        ...user,
      })
      .then(({ data }) => {
        if (profile.profileId === profileId)
          setProfile((prev) => ({
            ...prev,
            name: data.data.name,
          }));
        if (imgSrc) patchImg(data.data.profileId);
      })
      .catch(({ response }) => {
        console.log(response);
        console.log(response.status);
        console.log(response.data.message);
      });
  };

  const editHandler = () => {
    console.log('PATCH 요청');
    patchProfile();
    resetModal();
    setTimeout(() => {
      navigate(profile.profileId === profileId ? '/myprofile' : '/admin');
    }, 500);
  };

  const postProfile = async () => {
    await axios
      .post(`/profiles/${sessionStorage.getItem('userId')}`, {
        ...user,
      })
      .then(({ data }) => {
        if (imgSrc) patchImg(data.data.profileId);
        localStorage.removeItem('addProfile');
      })
      .catch(({ response }) => {
        console.log(response);
        console.log(response.status);
        console.log(response.data.message);
      });
  };

  const addHandler = () => {
    console.log('POST 요청');
    postProfile();
    resetModal();
    setTimeout(() => {
      navigate(`/admin`);
    }, 500);
  };

  const editConfirmProps = {
    isOpen: true,
    modalType: 'confirm',
    props: {
      text: `현재 입력된 내용으로
    프로필을 수정하시겠습니까?`,
      modalHandler: editHandler,
    },
  };

  const addConfirmProps = {
    isOpen: true,
    modalType: 'confirm',
    props: {
      text: `현재 입력된 내용으로
    프로필을 추가하시겠습니까?`,
      modalHandler: addHandler,
    },
  };

  const subjectTitles = subjects.map((obj) => obj.subjectTitle);

  const inputHandler = (e) => {
    const { name, value } = e.target;
    setUser({ ...user, [name]: value });
  };

  const submitHandler = (e) => {
    e.preventDefault();
    const { way, gender, pay, wantDate } = user;
    if (!(way && gender && pay && wantDate)) {
      setModal(requiredProps);
    } else {
      isNew ? setModal(addConfirmProps) : setModal(editConfirmProps);
    }
  };

  const imgModalProps = {
    isOpen: true,
    modalType: 'imgLoad',
    props: { setImgFile, setImgSrc },
  };

  return (
    <div className={styles.container}>
      <form id="profile" onSubmit={(e) => submitHandler(e)}>
        <div className={styles.userImage}>
          <img
            src={imgSrc || profileImage?.url || defaultUser}
            alt="profile-img"
          />
          <button type="button" onClick={() => setModal(imgModalProps)}>
            <MdMode />
          </button>
        </div>
        <span className={styles.required}>
          <span className={styles.requiredIcon} />은 필수 입력 사항입니다.
        </span>
        <div className={styles.userInfo}>
          <LabelTextInput
            id="name"
            name="이름"
            placeHolder="이름"
            type="text"
            value={name}
            handler={inputHandler}
            required
          />
          <LabelTextInput
            id="bio"
            name="한 줄 소개"
            placeHolder="한 줄 소개"
            type="text"
            value={bio}
            handler={inputHandler}
            required
          />
          <LabelTextInput
            id="school"
            name={profileStatus === 'TUTOR' ? '학교 / 학번' : '학년'}
            placeHolder={profileStatus === 'TUTEE' ? '학교 / 학번' : '학년'}
            type="text"
            value={school}
            handler={inputHandler}
            required
          />
        </div>
        <div className={styles.subject}>
          과목
          <span className={styles.requiredIcon} />
          <SubjectsButtons subjectTitles={subjectTitles} setUser={setUser} />
        </div>
        {isNew ? (
          <ButtonNightBlue text="추가완료" form="profile" type="submit" />
        ) : (
          <ButtonNightBlue text="수정완료" form="profile" type="submit" />
        )}
      </form>
    </div>
  );
};

ChangeProfileCard.propTypes = {
  isNew: PropType.bool,
  user: PropType.object,
  setUser: PropType.func,
};

export default ChangeProfileCard;
