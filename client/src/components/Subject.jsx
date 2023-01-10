import styles from './Subject.module.css';
import PropTypes from 'prop-types';

export const BlueSubject = ({ text }) => {
  return <div className={styles.blueSubject}>{text}</div>;
};

export const GraySubject = ({ text }) => {
  return <div className={styles.graySubject}>{text}</div>;
};

BlueSubject.propTypes = {
  text: PropTypes.string,
  modalHandler: PropTypes.func,
};

GraySubject.propTypes = {
  text: PropTypes.string,
  modalHandler: PropTypes.func,
};
