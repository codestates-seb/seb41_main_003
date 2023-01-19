import styles from './LoadingIndicator.module.css';

const LoadingIndicator = () => {
  return (
    <div className={styles.contain}>
      <div className={styles.loadingSpinner}>
        <div></div>
        <div></div>
        <div></div>
        <div></div>
        <div></div>
        <div></div>
        <div></div>
        <div></div>
        <div></div>
        <div></div>
        <div></div>
        <div></div>
      </div>
    </div>
  );
};

export default LoadingIndicator;
