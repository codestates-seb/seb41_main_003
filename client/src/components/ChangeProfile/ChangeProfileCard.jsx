import { LabelTextInput } from '../Input';
import { ButtonNightBlue } from '../Button';
import styles from './ChangeProfileCard.module.css';
import { MdMode } from 'react-icons/md';
import PropType from 'prop-types';
import SubjectsButtons from './SubjectsButtons';
import ModalState from '../../recoil/modal.js';
import { useSetRecoilState, useResetRecoilState } from 'recoil';

const ChangeProfileCard = ({ isNew = true, user, setUser }) => {
  const { name, bio, school, subjects, profileStatus } = user;

  const setModal = useSetRecoilState(ModalState);
  const resetModal = useResetRecoilState(ModalState);

  const requiredProps = {
    isOpen: true,
    modalType: 'alert',
    props: { text: '필수 입력 사항을 모두 작성해주세요.' },
  };

  const confirmHandler = () => {
    console.log('PATCH 요청'); // TODO : PATCH 요청
    resetModal();
  };
  const confirmProps = {
    isOpen: true,
    modalType: 'confirm',
    props: {
      text: `현재 입력된 내용으로
    프로필을 수정하시겠습니까?`,
      modalHandler: confirmHandler,
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
      setModal(confirmProps);
    }
  };

  return (
    <div className={styles.container}>
      <form id="profile" onSubmit={(e) => submitHandler(e)}>
        <div className={styles.userImage}>
          <div className={styles.image} />
          <button>
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
        <ButtonNightBlue
          text={isNew ? '추가완료' : '수정완료'}
          form="profile"
          type="submit"
        />
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
