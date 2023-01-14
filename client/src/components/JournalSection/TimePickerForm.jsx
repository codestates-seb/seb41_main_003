import styles from './TimePickerForm.module.css';
import { useState } from 'react';
import DatePicker from 'react-datepicker';
import PropTypes from 'prop-types';

// import 'react-datepicker/dist/react-datepicker.css';

const TimePickerForm = () => {
  const [startDate, setStartDate] = useState(new Date());
  return (
    <div>
      <DatePicker
        selected={startDate}
        onChange={(date) => setStartDate(date)}
        showTimeSelect
        showTimeSelectOnly
        timeFormat="p"
        //timeFormat="HH:mm"
        timeIntervals={15}
        dateFormat="Pp"
        timeCaption="time"
      />
    </div>
  );
};

export default TimePickerForm;
