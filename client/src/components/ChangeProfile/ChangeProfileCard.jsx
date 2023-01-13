import { LabelTextInput } from '../Input';
import { ButtonNightBlue } from '../Button';
import styles from './ChangeProfileCard.module.css';
import { MdMode } from 'react-icons/md';
import PropType from 'prop-types';

const ChangeProfileCard = ({
  isNew = true,
  user,
  setUser,
  setIsConfirm,
  setIsRequired,
}) => {
  const { name, bio, school, subjects, profile_status } = user;

  const subjectTitles = subjects.map((obj) => obj.subjectTitle);

  const inputHandler = (e) => {
    const { name, value } = e.target;
    setUser({ ...user, [name]: value });
  };

  const submitHandler = (e) => {
    e.preventDefault();
    const { way, gender, pay, want_date } = user;
    if (!(way && gender && pay && want_date)) {
      setIsRequired((prev) => !prev);
    } else {
      setIsConfirm((prev) => !prev);
    }
  };

  const subjectHandler = (e) => {
    const { name } = e.target;
    const id = ['_', '영어', '수학', '국어', '사회', '과학', '자격증', '기타'];
    if (subjectTitles.includes(name)) {
      setUser({
        ...user,
        subjects: user.subjects.filter((obj) => obj.subjectTitle !== name),
      });
    } else {
      setUser({
        ...user,
        subjects: [
          ...user.subjects,
          {
            subjectId: id.indexOf(name),
            subjectTitle: name,
            content: '',
          },
        ],
      });
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
            name={profile_status === 'TUTOR' ? '학교 / 학번' : '학년'}
            placeHolder={profile_status === 'TUTEE' ? '학교 / 학번' : '학년'}
            type="text"
            value={school}
            handler={inputHandler}
            required
          />
        </div>
        <div className={styles.subject}>
          과목
          <span className={styles.requiredIcon} />
          <div className={styles.btnContain}>
            <button
              type="button"
              name="영어"
              className={subjectTitles.includes('영어') ? styles.active : ''}
              onClick={subjectHandler}
            >
              영어
            </button>
            <button
              type="button"
              name="수학"
              className={subjectTitles.includes('수학') ? styles.active : ''}
              onClick={subjectHandler}
            >
              수학
            </button>
            <button
              type="button"
              name="국어"
              className={subjectTitles.includes('국어') ? styles.active : ''}
              onClick={subjectHandler}
            >
              국어
            </button>
            <button
              type="button"
              name="사회"
              className={subjectTitles.includes('사회') ? styles.active : ''}
              onClick={subjectHandler}
            >
              사회
            </button>
            <button
              type="button"
              name="과학"
              className={subjectTitles.includes('과학') ? styles.active : ''}
              onClick={subjectHandler}
            >
              과학
            </button>
            <button
              type="button"
              name="자격증"
              className={subjectTitles.includes('자격증') ? styles.active : ''}
              onClick={subjectHandler}
            >
              자격증
            </button>
            <button
              type="button"
              name="기타"
              className={subjectTitles.includes('기타') ? styles.active : ''}
              onClick={subjectHandler}
            >
              기타
            </button>
          </div>
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
