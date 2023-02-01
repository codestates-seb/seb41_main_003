import { forwardRef } from 'react';
import styles from './LoadingIndicator.module.css';
import PropType from 'prop-types';

const LoadingIndicator = forwardRef(({ isLoading, isSmall = false }, ref) => {
  return (
    <div
      className={`${styles.contain} ${isSmall ? styles.small : ''}`}
      ref={ref}
    >
      {isLoading && (
        <div
          className={`${styles.loadingSpinner} ${isSmall ? styles.small : ''}`}
        >
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
      )}
    </div>
  );
});

LoadingIndicator.propTypes = {
  isLoading: PropType.bool,
  isSmall: PropType.bool,
};

LoadingIndicator.displayName = 'LoadingIndicator';

export default LoadingIndicator;
