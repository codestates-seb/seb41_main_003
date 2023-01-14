import styles from './ChangeProfileContents.module.css';
import { Textarea, TextInput } from '../Input';
import PropType from 'prop-types';

const TuteeContents = ({ user, setUser }) => {
  const { way, gender, character, pay, wantDate, preTutoring } = user;

  const inputHandler = (e) => {
    const { name, value } = e.target;
    setUser({ ...user, [name]: value });
  };

  return (
    <div className={styles.container}>
      <form>
        <div className={styles.inputContainer}>
          <label htmlFor="way">
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
          <label htmlFor="gender">
            <h4>
              성별
              <span className={styles.requiredIcon} />
            </h4>
          </label>
          <div>
            <TextInput
              id="gender"
              type="text"
              handler={inputHandler}
              value={gender}
              placeHolder="성별을 입력하세요."
              required
            />
            <span className={styles.required}>
              {!gender && '내용을 작성해야 합니다.'}
            </span>
          </div>
        </div>
        <div className={styles.inputContainer}>
          <label htmlFor="pay">
            <h4>
              예산
              <span className={styles.requiredIcon} />
            </h4>
          </label>
          <div className={styles.textareaContainer}>
            <Textarea
              id="pay"
              handler={inputHandler}
              value={pay}
              placeHolder="수업료 예산을 작성하세요."
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
          <label htmlFor="way">
            <h4>
              선생님께 바라는 것<span className={styles.requiredIcon} />
            </h4>
          </label>
          <div className={styles.textareaContainer}>
            <Textarea
              id="way"
              handler={inputHandler}
              value={way}
              placeHolder="선생님께 바라는 점을 자유롭게 작성하세요."
              required
            />
            <span className={styles.required}>
              {!way && '내용을 작성해야 합니다.'}
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
              placeHolder="시범 과외 희망 여부를 작성해주세요."
            />
          </div>
        </div>
      </form>
    </div>
  );
};

TuteeContents.propTypes = {
  user: PropType.object,
  setUser: PropType.func,
};

export default TuteeContents;
