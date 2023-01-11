import styles from './Button.module.css';
import PropTypes from 'prop-types';

const ButtonDefault = ({ text, buttonHandler }) => {
  return (
    <button onClick={buttonHandler} className={styles.default}>
      {text}
    </button>
  );
};

ButtonDefault.propTypes = {
  text: PropTypes.string,
  buttonHandler: PropTypes.func,
};

const ButtonNightBlue = ({ text, buttonHandler }) => {
  return (
    <button onClick={buttonHandler} className={styles.nightBlue}>
      {text}
    </button>
  );
};

ButtonNightBlue.propTypes = {
  text: PropTypes.string,
  buttonHandler: PropTypes.func,
};

const ButtonRed = ({ text, buttonHandler }) => {
  return (
    <button onClick={buttonHandler} className={styles.red}>
      {text}
    </button>
  );
};

ButtonRed.propTypes = {
  text: PropTypes.string,
  buttonHandler: PropTypes.func,
};
const ButtonSnow = ({ text, buttonHandler }) => {
  return (
    <button onClick={buttonHandler} className={styles.snow}>
      {text}
    </button>
  );
};

ButtonSnow.propTypes = {
  text: PropTypes.string,
  buttonHandler: PropTypes.func,
};

const ButtonSilver = ({ text, buttonHandler }) => {
  return (
    <button onClick={buttonHandler} className={styles.silver}>
      {text}
    </button>
  );
};

ButtonSilver.propTypes = {
  text: PropTypes.string,
  buttonHandler: PropTypes.func,
};
export { ButtonDefault, ButtonNightBlue, ButtonRed, ButtonSnow, ButtonSilver };
