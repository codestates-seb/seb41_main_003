import styles from './DatePickerForm.module.css';
import { useState, useEffect } from 'react';
import DatePicker from 'react-datepicker';
import PropTypes from 'prop-types';
import { VscTriangleDown } from 'react-icons/vsc';
import 'react-datepicker/dist/react-datepicker.css';
import './DatePicker.css';

//"1월 10일 09:00" or "Thu Jan 05 2023 22:20:58"
const DatePickerForm = ({ user, setUser }) => {
  const [startDate, setStartDate] = useState(new Date());
  const [year, setYear] = useState(new Date().getFullYear());
  const [month, setMonth] = useState(new Date().getMonth() + 1);
  const [day, setDay] = useState(new Date().getDate());

  const [startTime, setStartTime] = useState(new Date());
  const [hours, setHours] = useState(new Date().getHours());
  const [minutes, setMinutes] = useState(new Date().getMinutes());
  const [seconds, setSeconds] = useState(new Date().getSeconds());

  useEffect(() => {
    // console.log('날짜 변경이 반영되어서 랜더');
  }, startDate);
  useEffect(() => {
    // console.log('시간 변경이 반영되어서 랜더');
  }, startTime);

  console.log(startTime, 'sososososo');

  const getPickedDate = (date) => {
    setYear(date.getFullYear());
    setDay(('0' + date.getDate()).slice(-2));
    setMonth(('0' + (date.getMonth() + 1)).slice(-2));
  };

  const getPickedTime = (date) => {
    setHours(('0' + date.getHours()).slice(-2));
    setMinutes(('0' + date.getMinutes()).slice(-2));
    setSeconds(('0' + date.getSeconds()).slice(-2));
  };

  const DateInputButton = ({ onClick }) => (
    <button className={styles.iconButton} onClick={onClick}>
      <VscTriangleDown size="24px" />
    </button>
  );
  const TimeInputButton = ({ onClick }) => (
    <button className={styles.iconButtonT} onClick={onClick}>
      <VscTriangleDown size="12px" />
    </button>
  );

  const userEditHandler = () => {
    setUser({ ...user, startTime: 'Change to', endTime: 'Change to' });
    console.log('startTime이 변경되었습니다');
    //실제로 데이터 바꿔주는 함수
  };
  console.log(user, '손자컴포넌트');

  // 시작날짜 시작 시간 끝나는 시간
  // 날짜 + 시작시간
  // 날짜 + 끝시간

  return (
    <div className={styles.container}>
      <div className={styles.line1}>
        <p className={styles.font1}>{day}</p>
        <DatePicker
          showPopperArrow={false}
          className={styles.calendar}
          onChange={(date) => setStartDate(date)}
          onSelect={getPickedDate}
          selected={startDate}
          dateFormat="h:mm aa" //"h:mm aa"
          customInput={<DateInputButton />}
        />
      </div>
      <div className={styles.line2}>
        <p className={styles.font4}>
          {year}년 {month}월
        </p>
      </div>
      <div className={styles.line3}>
        <div className={styles.font6}>09:00~18:00</div>
        <div>
          <DatePicker
            showPopperArrow={false}
            className={styles.calendar}
            onChange={(date) => setStartTime(date)}
            onSelect={getPickedTime}
            selected={startTime}
            showTimeSelect
            showTimeSelectOnly
            timeIntervals={15}
            dateFormat="h:mm aa"
            customInput={<TimeInputButton />}
          />
        </div>
      </div>
    </div>
  );
};
DatePickerForm.propTypes = {
  onClick: PropTypes.func,
  value: PropTypes.func,
  user: PropTypes.object,
  setUser: PropTypes.func,
};
export default DatePickerForm;
