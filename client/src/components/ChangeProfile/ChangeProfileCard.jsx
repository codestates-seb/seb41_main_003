import { LabelTextInput } from '../Input';
import { ButtonNightBlue } from '../Button';
import styles from './ChangeProfileCard.module.css';
import { MdMode } from 'react-icons/md';
import PropType from 'prop-types';
import SubjectsButtons from './SubjectsButtons';
import ModalState from '../../recoil/modal.js';
import { useSetRecoilState, useResetRecoilState } from 'recoil';
import axios from 'axios';
import { useParams } from 'react-router-dom';
import reIssueToken from '../../util/reIssueToken';
import defaultUser from '../../assets/defaultUser.png';

const ChangeProfileCard = ({ isNew = true, user, setUser }) => {
  const { name, bio, school, subjects, profileStatus, profileImage } = user;

  const userId =
    sessionStorage.getItem('userId') || localStorage.getItem('userId');
  const { profileId } = useParams();

  const setModal = useSetRecoilState(ModalState);
  const resetModal = useResetRecoilState(ModalState);

  const requiredProps = {
    isOpen: true,
    modalType: 'alert',
    props: { text: '필수 입력 사항을 모두 작성해주세요.' },
  };

  const patchProfile = async () => {
    await axios
      .patch(
        `${process.env.REACT_APP_BASE_URL}/profiles/details/${profileId}`,
        {
          ...user,
        },
        {
          headers: {
            Authorization:
              sessionStorage.getItem('authorization') ||
              localStorage.getItem('authorization'),
          },
        }
      )
      .then(() => (window.location.href = '/admin'))
      .catch(({ response }) => {
        if (response.data.message === 'EXPIRED ACCESS TOKEN') {
          reIssueToken(patchProfile).catch(() => {
            console.log(response);
            window.location.href = '/login';
          });
        }
      });
  };

  const editHandler = () => {
    console.log('PATCH 요청');
    patchProfile();
    resetModal();
    window.location.href = `/myprofile/${profileId}`;
  };

  //프로필 추가 4개 초과시에는 400 에러
  const postProfile = async () => {
    await axios
      .post(
        `${process.env.REACT_APP_BASE_URL}/profiles/${
          sessionStorage.getItem('userId') || localStorage.getItem('userId')
        }`,
        {
          ...user,
        },
        {
          headers: {
            Authorization:
              sessionStorage.getItem('authorization') ||
              localStorage.getItem('authorizaiton'),
          },
        }
      )
      .then(() => (window.location.href = '/admin'))
      .catch(({ response }) => {
        if (response.data.message === 'EXPIRED ACCESS TOKEN') {
          reIssueToken(postProfile).catch(() => {
            console.log(response);
            window.location.href = '/login';
          });
        }
      });
  };

  const addHandler = () => {
    console.log('POST 요청');
    postProfile();
    resetModal();
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
    props: { setUser: setUser, profileId: profileId },
  };

  return (
    <div className={styles.container}>
      <form id="profile" onSubmit={(e) => submitHandler(e)}>
        <div className={styles.userImage}>
          <img src={profileImage.url || defaultUser} alt="profile-img" />
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
