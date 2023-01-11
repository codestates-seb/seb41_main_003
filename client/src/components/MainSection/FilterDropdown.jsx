import styles from './FilterDropdown.module.css';

const FilterDropdown = () => {
  return (
    <ul className={styles.dropdownContainer}>
      <li className={styles.dropdown}>최신 순</li>
      <li className={styles.dropdown}>평점 순</li>
    </ul>
  );
};

export default FilterDropdown;
