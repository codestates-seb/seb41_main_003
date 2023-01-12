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

export { ButtonDefault, ButtonNightBlue, ButtonRed, ButtonSnow, ButtonSilver };
