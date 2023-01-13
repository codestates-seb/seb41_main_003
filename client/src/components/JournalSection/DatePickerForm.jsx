import styles from './DatePickerForm.module.css';
import { useState } from 'react';
import DatePicker from 'react-datepicker';
import PropTypes from 'prop-types';
import { VscTriangleDown } from 'react-icons/vsc';
import 'react-datepicker/dist/react-datepicker.css';
import './DatePicker.css';
const DatePickerForm = () => {
  const [startDate, setStartDate] = useState(new Date());
  const [year, setYear] = useState(new Date().getFullYear());
  const [month, setMonth] = useState(new Date().getMonth() + 1);
  const [day, setDay] = useState(new Date().getDate());

  const handleDateSelect = (date) => {
    setDay(date.getDate());
    setMonth(date.getMonth() + 1);
    setYear(date.getFullYear());
  };

  const DateOpenButton = ({ value, onClick }) => (
    <button className={styles.iconButton} onClick={onClick}>
      <VscTriangleDown size="24px" />
    </button>
  );
  console.log(startDate);
  console.log(new Date());

  // 시작날짜 시작 시간 끝나는 시간
  // 날짜 + 시작시간
  // 날짜 + 끝시간

  return (
    <div className={styles.container}>
      <div className={styles.textContainer}>
        <div className={styles.flexContainer}>
          <p className={styles.font1}>{day}</p>
          <div>
            <DatePicker
              showPopperArrow={false}
              className={styles.calendar} // 클래스 명 지정
              selected={startDate}
              onChange={(date) => setStartDate(date)}
              onSelect={handleDateSelect}
              customInput={<DateOpenButton />}
            />
          </div>
        </div>
        <p className={styles.font4}>
          {year}년/{month}월
        </p>
      </div>
    </div>
  );
};
DatePickerForm.propTypes = {
  onClick: PropTypes.func,
  value: PropTypes.func,
};
export default DatePickerForm;
