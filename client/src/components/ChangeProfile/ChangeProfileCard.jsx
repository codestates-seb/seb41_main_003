import { LabelTextInput } from '../Input';
import { ButtonNightBlue } from '../Button';
import styles from './ChangeProfileCard.module.css';
import { MdMode } from 'react-icons/md';
import PropType from 'prop-types';
import SubjectsButtons from './SubjectsButtons';
import ModalState from '../../recoil/modal.js';
import { useSetRecoilState } from 'recoil';

const ChangeProfileCard = ({
  isNew = true,
  user,
  setUser,
  setIsConfirm,
  setIsRequired,
}) => {
  const { name, bio, school, subjects, profileStatus } = user;

  const setModal = useSetRecoilState(ModalState);

  const requiredProps = {
    isOpen: true,
    modalType: 'alert',
    props: { text: '필수 입력 사항을 모두 작성해주세요.' },
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
      setIsConfirm((prev) => !prev);
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
  setIsConfirm: PropType.func,
  setIsRequired: PropType.func,
};

export default ChangeProfileCard;
