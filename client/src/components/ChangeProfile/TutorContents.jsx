import styles from './ChangeProfileContents.module.css';
import { Textarea, TextInput } from '../Input';
import PropType from 'prop-types';

const TutorContents = ({ user, setUser }) => {
  const {
    way,
    subjects,
    difference,
    gender,
    character,
    pay,
    want_date,
    pre_tutoring,
  } = user;

  const inputHandler = (e) => {
    const { name, value } = e.target;
    setUser({ ...user, [name]: value });
  };

  const subjectHandler = (e) => {
    const { name, value } = e.target;
    setUser({
      ...user,
      subjects: user.subjects.map((obj) =>
        obj.subjectTitle === name
          ? {
              subjectId: obj.subjectId,
              subjectTitle: obj.subjectTitle,
              content: value,
            }
          : obj
      ),
    });
  };

  return (
    <div className={styles.container}>
      <div className={styles.inputContainer}>
        <label htmlFor="way">
          <h4>수업방식</h4>
        </label>
        <div className={styles.textareaContainer}>
          <Textarea
            id="way"
            handler={inputHandler}
            value={way}
            placeHolder="수업방식에 대해 자유롭게 작성하세요."
          />
        </div>
      </div>
      <div className={styles.inputContainer}>
        <h4>과목별 수업 내용</h4>
        {subjects.map(({ subjectTitle, content, subjectId }) => (
          <div className={styles.subjectInput} key={subjectId}>
            <label htmlFor="subject">
              <div className="subject">{subjectTitle}</div>
            </label>
            <Textarea
              id={subjectTitle}
              placeHolder="수업방식에 대해 자유롭게 작성하세요."
              value={content}
              handler={subjectHandler}
            />
          </div>
        ))}
      </div>
      <div className={styles.inputContainer}>
        <label htmlFor="difference">
          <h4>차별점</h4>
        </label>
        <div className={styles.textareaContainer}>
          <Textarea
            id="difference"
            handler={inputHandler}
            value={difference}
            placeHolder="다른 과외와의 차별점을 자유롭게 작성하세요."
          />
        </div>
      </div>
      <div className={styles.inputContainer}>
        <label htmlFor="gender">
          <h4>성별</h4>
        </label>
        <div className={styles.inlineInput}>
          <TextInput
            id="gender"
            type="text"
            handler={inputHandler}
            value={gender}
            placeHolder="성별을 입력하세요."
          />
        </div>
      </div>
      <div className={styles.inputContainer}>
        <label htmlFor="character">
          <h4>성격</h4>
        </label>
        <div className={styles.textareaContainer}>
          <Textarea
            id="character"
            handler={inputHandler}
            value={character}
            placeHolder="본인의 성격을 작성해주세요."
          />
        </div>
      </div>
      <div className={styles.inputContainer}>
        <label htmlFor="pay">
          <h4>수업료</h4>
        </label>
        <div className={styles.textareaContainer}>
          <Textarea
            id="pay"
            handler={inputHandler}
            value={pay}
            placeHolder="수업료와 그 기준을 작성해주세요."
          />
        </div>
      </div>
      <div className={styles.inputContainer}>
        <label htmlFor="want_date">
          <h4>과외 가능 요일 / 시간</h4>
        </label>
        <div className={styles.textareaContainer}>
          <Textarea
            id="want_date"
            handler={inputHandler}
            value={want_date}
            placeHolder="과외가 가능한 요일과 시간을 작성해주세요."
          />
        </div>
      </div>
      <div className={styles.inputContainer}>
        <label htmlFor="pre_tutoring">
          <h4>시범 과외 가능 여부</h4>
        </label>
        <div className={styles.textareaContainer}>
          <Textarea
            id="pre_tutoring"
            handler={inputHandler}
            value={pre_tutoring}
            placeHolder="시범 과외 가능 여부를 작성해주세요."
          />
        </div>
      </div>
    </div>
  );
};

TutorContents.propTypes = {
  user: PropType.object,
  setUser: PropType.func,
};

export default TutorContents;
