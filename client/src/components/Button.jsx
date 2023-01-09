import styles from './Button.module.css';
import PropTypes from 'prop-types';
const ButtonDefault = ({ content, buttonHandler }) => {
  return (
    <button onClick={buttonHandler} className={styles.default}>
      {content}
    </button>
  );
};
ButtonDefault.propTypes = {
  content: PropTypes.string,
  buttonHandler: PropTypes.func,
};
const ButtonNightBlue = ({ content, buttonHandler }) => {
  return (
    <button onClick={buttonHandler} className={styles.nightBlue}>
      {content}
    </button>
  );
};
ButtonNightBlue.propTypes = {
  content: PropTypes.string,
  buttonHandler: PropTypes.func,
};
const ButtonRed = ({ content, buttonHandler }) => {
  return (
    <button onClick={buttonHandler} className={styles.red}>
      {content}
    </button>
  );
};
ButtonRed.propTypes = {
  content: PropTypes.string,
  buttonHandler: PropTypes.func,
};
const ButtonSnow = ({ content, buttonHandler }) => {
  return (
    <button onClick={buttonHandler} className={styles.snow}>
      {content}
    </button>
  );
};
ButtonSnow.propTypes = {
  content: PropTypes.string,
  buttonHandler: PropTypes.func,
};
const ButtonSilver = ({ content, buttonHandler }) => {
  return (
    <button onClick={buttonHandler} className={styles.silver}>
      {content}
    </button>
  );
};
ButtonSilver.propTypes = {
  content: PropTypes.string,
  buttonHandler: PropTypes.func,
};
export { ButtonDefault, ButtonNightBlue, ButtonRed, ButtonSnow, ButtonSilver };
