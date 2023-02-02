import styles from './Button.module.css';
import PropTypes from 'prop-types';

const ButtonDefault = ({ text, name, buttonHandler }) => {
  return (
    <button name={name} onClick={buttonHandler} className={styles.default}>
      {text}
    </button>
  );
};
ButtonDefault.propTypes = {
  text: PropTypes.string,
  name: PropTypes.string,
  buttonHandler: PropTypes.func,
};

const ButtonNightBlue = ({ text, name, buttonHandler }) => {
  return (
    <button name={name} onClick={buttonHandler} className={styles.nightBlue}>
      {text}
    </button>
  );
};
ButtonNightBlue.propTypes = {
  text: PropTypes.string,
  name: PropTypes.string,
  buttonHandler: PropTypes.func,
};

const ButtonRed = ({ text, name, buttonHandler }) => {
  return (
    <button name={name} onClick={buttonHandler} className={styles.red}>
      {text}
    </button>
  );
};
ButtonRed.propTypes = {
  text: PropTypes.string,
  name: PropTypes.string,
  buttonHandler: PropTypes.func,
};

const ButtonSnow = ({ text, name, buttonHandler }) => {
  return (
    <button name={name} onClick={buttonHandler} className={styles.snow}>
      {text}
    </button>
  );
};
ButtonSnow.propTypes = {
  text: PropTypes.string,
  name: PropTypes.string,
  buttonHandler: PropTypes.func,
};

const ButtonSilver = ({ text, name, buttonHandler }) => {
  return (
    <button name={name} onClick={buttonHandler} className={styles.silver}>
      {text}
    </button>
  );
};
ButtonSilver.propTypes = {
  text: PropTypes.string,
  name: PropTypes.string,
  buttonHandler: PropTypes.func,
};

/**
 * 페이지 최상단으로 올리는 TOP 버튼입니다. 클래스명을 사용해 위치를 직접 잡아야 합니다.
 * @className .buttonTop
 */
const ButtonTop = () => {
  return (
    <div className="buttonTop">
      <button
        name="topBtn"
        className={styles.top}
        onClick={() =>
          window.scrollTo({
            behavior: 'smooth',
            top: 0,
          })
        }
      >
        <svg viewBox="0 0 14 12" xmlns="http://www.w3.org/2000/svg">
          <path d="M7 0L13.9282 12H0.0717969L7 0Z" />
        </svg>

        <span>TOP</span>
      </button>
    </div>
  );
};

export {
  ButtonDefault,
  ButtonNightBlue,
  ButtonRed,
  ButtonSnow,
  ButtonSilver,
  ButtonTop,
};
