<?php

namespace App\Model;


class Otp extends Model
{
    //
    protected $table = 'otp';
    protected $primaryKey = 'id';
    const CREATED_AT = 'created_at';
    const UPDATED_AT = null;
    

    protected $fillable = [
        'user_id', 'uuid', 'code','expire_at'
    ];

}
