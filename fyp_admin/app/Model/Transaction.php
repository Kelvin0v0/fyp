<?php

namespace App\Model;


class Transaction extends Model
{
    //
    protected $table = 'transaction';
    protected $primaryKey = 'id';
    const CREATED_AT = 'created_at';
    const UPDATED_AT = 'updated_at';


    protected $fillable = [
        'sender_id','receiver_id', 'amount', 'status','type'
    ];

}
