import styles from './FilterDropdown.module.css';
import PropType from 'prop-types';
import { forwardRef } from 'react';

const FilterDropdown = forwardRef(({ setFilter }, ref) => {
  const clickHandler = (e) => {
    setFilter(e.target.name);
  };

  return (
    <ul className={styles.dropdownContainer} ref={ref}>
      <li className={styles.dropdown}>
        <button name="updateAt" onClick={clickHandler}>
          최신 순
        </button>
      </li>
      <li className={styles.dropdown}>
        <button name="rate" onClick={clickHandler}>
          평점 순
        </button>
      </li>
    </ul>
  );
});

FilterDropdown.propTypes = {
  setFilter: PropType.func,
};

FilterDropdown.displayName = 'FilterDropdown';

export default FilterDropdown;
