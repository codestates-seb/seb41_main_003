import { LabelTextInput } from '../Input';
import { ButtonNightBlue } from '../Button';
import styles from './ChangeProfileCard.module.css';
import { MdMode } from 'react-icons/md';
import { useState } from 'react';
import PropType from 'prop-types';

const ChangeProfileCard = ({ isNew = true }) => {
  const [subject, setSubject] = useState([]);

  const subjectHandler = (e) => {
    const { name } = e.target;
    if (subject.includes(name)) {
      setSubject(subject.filter((el) => el !== name));
    } else {
      setSubject([...subject, name]);
    }
    console.log(subject);
  };
  return (
    <div className={styles.container}>
      <div className={styles.userImage}>
        <div className={styles.image} />
        <button>
          <MdMode />
        </button>
      </div>
      <div className={styles.userInfo}>
        <LabelTextInput
          id="name"
          name="이름"
          placeHolder="이름"
          type="text"
          value=""
        />
        <LabelTextInput
          id="bio"
          name="한 줄 소개"
          placeHolder="한 줄 소개"
          type="text"
          value=""
        />
        <LabelTextInput
          id="school"
          name="학교 / 학번"
          placeHolder="학교 / 학번"
          type="text"
          value=""
        />
      </div>
      <div className={styles.subject}>
        과목
        <div className={styles.btnContain}>
          <button
            name="en"
            className={subject.includes('en') ? styles.active : ''}
            onClick={subjectHandler}
          >
            영어
          </button>
          <button
            name="ma"
            className={subject.includes('ma') ? styles.active : ''}
            onClick={subjectHandler}
          >
            수학
          </button>
          <button
            name="ko"
            className={subject.includes('ko') ? styles.active : ''}
            onClick={subjectHandler}
          >
            국어
          </button>
          <button
            name="so"
            className={subject.includes('so') ? styles.active : ''}
            onClick={subjectHandler}
          >
            사회
          </button>
          <button
            name="sc"
            className={subject.includes('sc') ? styles.active : ''}
            onClick={subjectHandler}
          >
            과학
          </button>
          <button
            name="ce"
            className={subject.includes('ce') ? styles.active : ''}
            onClick={subjectHandler}
          >
            자격증
          </button>
          <button
            name="etc"
            className={subject.includes('etc') ? styles.active : ''}
            onClick={subjectHandler}
          >
            기타
          </button>
        </div>
      </div>
      <ButtonNightBlue text={isNew ? '추가완료' : '수정완료'} />
    </div>
  );
};

ChangeProfileCard.propTypes = {
  isNew: PropType.bool,
};

export default ChangeProfileCard;
