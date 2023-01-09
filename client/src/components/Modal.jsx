import styles from './Modal.module.css';
import PropTypes from 'prop-types';

export const AlertModal = ({ text }) => {
  return (
    <div className={styles.backdrop}>
      <div className={styles.view}>
        <div className={styles.text}>{text}</div>
        <button className={styles.button}></button>
      </div>
    </div>
  );
};

export const ConfirmModal = ({ text }) => {
  return (
    <div className={styles.backdrop}>
      <div className={styles.view}>
        <div className={styles.text}>{text}</div>
        <button></button>
      </div>
    </div>
  );
};

AlertModal.propTypes = {
  text: PropTypes.string,
};

ConfirmModal.propTypes = {
  text: PropTypes.string,
};
