import styles from './ChangeProfileContents.module.css';
import { Textarea } from '../Input';
import PropType from 'prop-types';
import { useState, useEffect } from 'react';

//수정시 들어온 값을 이미 띄워주고 싶은데 select 박스에...

const TutorContents = ({ user, setUser }) => {
  const {
    way,
    subjects,
    difference,
    gender,
    character,
    pay,
    wantDate,
    preTutoring,
  } = user;
  const [genderValue, setGenderValue] = useState(gender);

  const inputHandler = (e) => {
    const { name, value } = e.target;
    setUser({ ...user, [name]: value });
  };

  const changeHandler = (e) => {
    setGenderValue(e.target.value);
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

  useEffect(() => {
    setUser({ ...user, gender: genderValue });
  }, [genderValue]);

  return (
    <div className={styles.container}>
      <form>
        <div className={styles.inputContainer}>
          <label htmlFor="way">
            <h4>
              수업방식
              <span className={styles.requiredIcon} />
            </h4>
          </label>
          <div className={styles.textareaContainer}>
            <Textarea
              id="way"
              handler={inputHandler}
              value={way}
              placeHolder="수업방식에 대해 자유롭게 작성하세요."
              required
            />
            <span className={styles.required}>
              {!way && '내용을 작성해야 합니다.'}
            </span>
          </div>
        </div>
        <div className={styles.inputContainer}>
          {subjects.length !== 0 && <h4>과목별 수업 내용</h4>}
          {subjects
            .sort((a, b) => a.subjectId - b.subjectId)
            .map(({ subjectTitle, content, subjectId }) => (
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
            <h4>
              성별
              <span className={styles.requiredIcon} />
            </h4>
          </label>
          <div className={styles.inlineInput}>
            <select
              id="gender"
              className={styles.selectBox}
              defaultValue={genderValue}
              key={genderValue}
              onChange={changeHandler}
              required
            >
              <option value="" onChange={changeHandler}>
                성별을 선택해주세요.
              </option>
              <option value="남성" onChange={changeHandler}>
                남성
              </option>
              <option value="여성" onChange={changeHandler}>
                여성
              </option>
              <option value="밝히지 않음" onChange={changeHandler}>
                밝히지 않음
              </option>
            </select>
            <span className={styles.required}>
              {genderValue === '' && '성별을 선택해주세요.'}
            </span>
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
            <h4>
              수업료
              <span className={styles.requiredIcon} />
            </h4>
          </label>
          <div className={styles.textareaContainer}>
            <Textarea
              id="pay"
              handler={inputHandler}
              value={pay}
              placeHolder="수업료와 그 기준을 작성해주세요."
              required
            />
            <span className={styles.required}>
              {!pay && '내용을 작성해야 합니다.'}
            </span>
          </div>
        </div>
        <div className={styles.inputContainer}>
          <label htmlFor="wantDate">
            <h4>
              과외 가능 요일 / 시간
              <span className={styles.requiredIcon} />
            </h4>
          </label>
          <div className={styles.textareaContainer}>
            <Textarea
              id="wantDate"
              handler={inputHandler}
              value={wantDate}
              placeHolder="과외가 가능한 요일과 시간을 작성해주세요."
              required
            />
            <span className={styles.required}>
              {!wantDate && '내용을 작성해야 합니다.'}
            </span>
          </div>
        </div>
        <div className={styles.inputContainer}>
          <label htmlFor="preTutoring">
            <h4>시범 과외 가능 여부</h4>
          </label>
          <div className={styles.textareaContainer}>
            <Textarea
              id="preTutoring"
              handler={inputHandler}
              value={preTutoring}
              placeHolder="시범 과외 가능 여부를 작성해주세요."
            />
          </div>
        </div>
      </form>
    </div>
  );
};

TutorContents.propTypes = {
  user: PropType.object,
  setUser: PropType.func,
};

export default TutorContents;
